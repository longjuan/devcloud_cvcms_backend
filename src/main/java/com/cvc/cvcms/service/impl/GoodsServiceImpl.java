package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.dao.GoodsDao;
import com.cvc.cvcms.dao.PromotionDao;
import com.cvc.cvcms.pojo.Goods;
import com.cvc.cvcms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author XiuGitHung
 * @date 2021/3/28 17:125
 * @desc goods的service层
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    PromotionDao promotionDao;

    @Override
    public boolean addGood(Goods goods) {
        return goodsDao.addGood(goods) > 0;
    }

    @Override
    public String deleteGoods(Integer goodsId) {
        if(findGoodsById(goodsId) == null) return null;
        goodsDao.deleteGoods(goodsId);
        return "删除成功";
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return goodsDao.updateGoods(goods) > 0;
    }

    @Override
    public List<Goods> getGoodsByConditions(String goodsName,String classification,Integer onSale, Integer page, Integer pagesize) {
        List<Goods> list = goodsDao.getGoodsByConditions(goodsName, classification, onSale, (page - 1) * pagesize, pagesize);
        for(Goods good : list){
            if(good.getEndTime() != null){
                Date endTime = good.getEndTime();
                Date nowTime = new Date();
                if(nowTime.compareTo(endTime) > 0){
                    good.setPromotionPrice(null);
                    promotionDao.deletePromotionById(good.getId());
                }
            }
        }
        return list;
    }

    @Override
    public int getNumOfFindGoodsByConditions(String goodsName,String classification,Integer onSale) {
        return goodsDao.getNumOfGoodsByConditions(goodsName,classification,onSale);
    }

    @Override
    public Goods findGoodsById(Integer id) {
        Goods goods = goodsDao.findGoodsById(id);
        if(goods == null) return null;
        return goods;
    }

    @Override
    public String GoodsUp(Integer id) {
        if(findGoodsById(id) == null) return null;
        goodsDao.GoodsUp(id);
        return "上架成功";
    }

    @Override
    public String GoodsDown(Integer id) {
        if(findGoodsById(id) == null) return null;
        goodsDao.GoodsDown(id);
        return "下架成功";
    }

    @Override
    public boolean allowDecimals(String barCode) throws BusinessException {
        Goods goods = goodsDao.getGoodsByBarCode(barCode);
        if (goods == null){
            throw new BusinessException("无此商品");
        }
        return goods.getStockDecimal().intValue() == 1;
    }

    @Override
    public Goods getGoodsByBarcode(String barcode) {
        return goodsDao.getGoodsByBarCode(barcode);
    }

    @Override
    public List<Goods> getGoodsBySupplierId(Integer supplierId) {
        return goodsDao.getGoodsBySupplierId(supplierId);
    }


}
