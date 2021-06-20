package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.dao.CouponDao;
import com.cvc.cvcms.dao.GoodsDao;
import com.cvc.cvcms.dao.SalesDao;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.pojo.*;
import com.cvc.cvcms.pojo.dto.SalesItemDTO;
import com.cvc.cvcms.pojo.dto.SettlementDTO;
import com.cvc.cvcms.service.CashieringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZZJ
 * @date 2021/3/29 12:59
 * @desc
 */
@Service
public class CashieringServiceImpl implements CashieringService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SalesDao salesDao;

    @Override
    public BigDecimal getPrice(String barCode) {
        Goods goods = goodsDao.getGoodsByBarCode(barCode);
        if (goods == null){
            return null;
        }
        return getCurrentPrice(goods,new Date());
    }

    /**
     * 获取现在的价格（判断时间）
     * @param goods
     * @return
     */
    @Override
    public BigDecimal getCurrentPrice(Goods goods, Date now){
        if (goods.getPromotionPrice() != null && goods.getStartTime().before(now) && goods.getEndTime().after(now)){
            return goods.getPromotionPrice();
        }
        return goods.getPrice();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal settlement(SettlementDTO settlementDTO) throws BusinessException {
        Date now = new Date();
        // 查出goods 保持数据一致性
        HashMap<String, Goods> goodsMap = getGoodsMapFromDTO(settlementDTO);
        // 如果有会员就查出会员
        User member = null;
        if (StringUtils.hasText(settlementDTO.getMember())){
            member = userDao.getUserByName(settlementDTO.getMember());
            if (member == null){
                throw new BusinessException("不存在该会员");
            }
        }

        BigDecimal preferentialAmount = new BigDecimal("0");
        // 如果有优惠券信息就验证
        if (settlementDTO.getMember() != null && settlementDTO.getCoupon()!=null && settlementDTO.getCoupon().size()!=0){
            preferentialAmount = getPreferentialAmount(settlementDTO,goodsMap,now);
        }
        // 计算金额
        BigDecimal total = new BigDecimal("0");
        for (SalesItemDTO item : settlementDTO.getItems()) {
            BigDecimal unitPrice = getPrice(item.getBarCode());
            Goods goods = goodsDao.getGoodsByBarCode(item.getBarCode());
            goods.setStock(goods.getStock().subtract(item.getQuantity()));
            goodsDao.updateGoods(goods);
            total = total.add(unitPrice.multiply(item.getQuantity())).setScale(2, RoundingMode.HALF_UP);
        }
        total = total.subtract(preferentialAmount).setScale(2, RoundingMode.HALF_UP);
        if (total.compareTo(settlementDTO.getEstimate()) != 0){
            throw new BusinessException("前端金额与实际金额不符，后端金额为"+total+"，请重新输入全部数据");
        }
        // 把购买记录加入数据库
        saveSales(settlementDTO,member,goodsMap,total,now);
        // 减少券
        if(settlementDTO.getCoupon() != null){
            for (String couponCode : settlementDTO.getCoupon()) {
                Coupon coupon = couponDao.getCouponByCode(couponCode);
                Integer quantity = couponDao.hasCouponQuantity(settlementDTO.getMember(), coupon.getId());
                if (quantity == 1){
                    couponDao.deleteUserCoupon(settlementDTO.getMember(),coupon.getId());
                }else{
                    couponDao.reduceUserCoupon(settlementDTO.getMember(),coupon.getId());
                }
            }
        }
        return total;
    }

    /**
     * 保存记录
     * @param settlementDTO
     * @param member
     * @param goodsMap
     * @param total
     */
    private void saveSales(SettlementDTO settlementDTO, User member, HashMap<String, Goods> goodsMap,
                           BigDecimal total,Date now){
        Integer memberId = member==null? null: member.getId();
        SalesRecord salesRecord = new SalesRecord(null,memberId,now,total);
        salesDao.saveSalesRecord(salesRecord);
        for (SalesItemDTO item : settlementDTO.getItems()) {
            Goods goods = goodsMap.get(item.getBarCode());
            BigDecimal currentPrice = getCurrentPrice(goods,now);
            BigDecimal quantity = item.getQuantity();
            SalesItem salesItem = new SalesItem(null,goods.getId(),goods.getGoodsName(),currentPrice,quantity,currentPrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP));
            salesDao.saveSalesItem(salesItem);
            SalesRecordToItem salesRecordToItem = new SalesRecordToItem(salesRecord.getId(), salesItem.getId());
            salesDao.saveSalesRecordToItem(salesRecordToItem);
        }
        if (settlementDTO.getCoupon() != null){
            for (String couponCode : settlementDTO.getCoupon()) {
                Coupon coupon = couponDao.getCouponByCode(couponCode);
                SalesCoupon salesCoupon = new SalesCoupon(salesRecord.getId(), coupon.getId(), coupon.getDecresedPrice());
                salesDao.saveSalesCoupon(salesCoupon);
            }
        }
    }


    /**
     * Controller调用这个方法
     * @param settlementDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public BigDecimal getPreferentialAmount(SettlementDTO settlementDTO) throws BusinessException {
        HashMap<String, Goods> goodsHashMap = getGoodsMapFromDTO(settlementDTO);
        return getPreferentialAmount(settlementDTO,goodsHashMap,new Date());
    }

    /**
     * 根据dto查出goods ，保持数据一致性
     * @param settlementDTO
     * @return key是barcode
     * @throws BusinessException 不存在该商品
     */
    private HashMap<String, Goods> getGoodsMapFromDTO(SettlementDTO settlementDTO) throws BusinessException {
        HashMap<String, Goods> goodsMap = new HashMap<>(settlementDTO.getItems().size());
        for (SalesItemDTO item : settlementDTO.getItems()) {
            Goods goods = goodsDao.getGoodsByBarCode(item.getBarCode());
            if (goods == null){
                throw new BusinessException(item.getBarCode()+"不存在该商品");
            }
            if(goods.getOnSale() == 0){
                throw new BusinessException(item.getBarCode()+"商品未上架");
            }
            goodsMap.put(goods.getBarCode(),goods);
        }
        return goodsMap;
    }

    /**
     * 保持数据一致性，要现在在公有方法中查出goods的实体类，再由此方法查询是否有券和是否满足条件
     * @param settlementDTO
     * @param goodsMap
     * @return
     * @throws BusinessException
     */
    private BigDecimal getPreferentialAmount(SettlementDTO settlementDTO,HashMap<String, Goods> goodsMap,Date now) throws BusinessException {
        List<Coupon> couponList = hasCoupon(settlementDTO.getMember(), settlementDTO.getCoupon(),now);
        BigDecimal preferential = isCouponMeet(settlementDTO.getItems(), couponList,goodsMap,now);
        return preferential;
    }

    /**
     * 商品是否满足优惠券条件
     * @param goods
     * @param coupons
     * @return 返回优惠金额 保留小数点后两位
     * @throws BusinessException 不满足就扔异常，满足什么事都没有
     */
    private BigDecimal isCouponMeet(List<SalesItemDTO> goods, List<Coupon> coupons,HashMap<String, Goods> goodsMap,Date now) throws BusinessException {
        BigDecimal preferential = new BigDecimal("0");
        for (Coupon coupon : coupons) {
            List<SalesItemDTO> effectiveGoods;
            if (coupon.getLimited()) {
                // 拿到适用的商品
                List<String> limitGoodsId = couponDao.getLimitGoodsBarCode(coupon.getId());
                // 求交集
                effectiveGoods = goods.stream().filter(item -> limitGoodsId.contains(item.getBarCode())).collect(Collectors.toList());

            }else{
                effectiveGoods = goods;
            }
            BigDecimal total = new BigDecimal("0");
            // 交集金额加起来
            for (SalesItemDTO item : effectiveGoods) {
                // 根据barcode从map里拿到goods实体类再获取价格 保持数据一致性
                BigDecimal price = getCurrentPrice(goodsMap.get(item.getBarCode()),now);
                total = total.add(price.multiply(item.getQuantity())).setScale(2, RoundingMode.HALF_UP);
            }
            if (coupon.getStartPrice().compareTo(total) > 0){
                throw new BusinessException(coupon.getCouponCode()+"不满足起始金额");
            }
            preferential = preferential.add(coupon.getDecresedPrice());
        }
        return preferential;
    }

    /**
     * 检查是否有券
     * @param member
     * @param couponCodeList
     * @return
     * @throws BusinessException
     */
    private List<Coupon> hasCoupon(String member, List<String> couponCodeList,Date now) throws BusinessException {
        List<Coupon> couponList = new ArrayList<>(couponCodeList.size());
        for (String couponCode : couponCodeList) {
            // 拿到券详细信息
            Coupon coupon = couponDao.getCouponByCode(couponCode);
            if(coupon == null){
                throw new BusinessException("优惠券不存在");
            }
            if (!(coupon.getStartTime().before(now) && coupon.getEndTime().after(now))){
                throw new BusinessException("某些券未在使用时间");
            }
            // 拿到拥有数量
            Integer quantity = couponDao.hasCouponQuantity(member, coupon.getId());
            // 添加进list减少数据库io
            couponList.add(coupon);
            if (quantity == null){
                throw new BusinessException("用户没有某些券");
            }
        }
        return couponList;
    }


}
