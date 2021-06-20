package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.Goods;
import com.cvc.cvcms.pojo.dto.SettlementDTO;
import com.cvc.cvcms.service.CashieringService;
import com.cvc.cvcms.service.CouponService;
import com.cvc.cvcms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author ZZJ
 * @date 2021/3/28 17:20
 * @desc
 */
@RestController
public class CashieringController {
    @Autowired
    private CashieringService cashieringService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CouponService couponService;

    @PostMapping("/goods/price")
//    @PreAuthorize("hasAuthority('')")
    public JsonStandard getPrice(String barCode) throws BusinessException {
        Goods goods = goodsService.getGoodsByBarcode(barCode);
        if (goods == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"无此商品");
        }
        if(goods.getOnSale() == 0){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"商品未上架");
        }
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("price",cashieringService.getCurrentPrice(goods,new Date()));
        result.put("barCode",barCode);
        result.put("allowDecimals",goodsService.allowDecimals(barCode));
        result.put("goodsName",goods.getGoodsName());
        return JsonStandard.success(result);
    }

    @PostMapping("/cashiering/settlement")
    public JsonStandard settlement(@RequestBody SettlementDTO settlement){
        if (settlement.getCoupon() != null) {
            HashSet<String> set = new HashSet<>(settlement.getCoupon());
            if(set.size() != settlement.getCoupon().size()){
                return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"有重复优惠券");
            }
        }
        try {
            // 主方法
            BigDecimal money = cashieringService.settlement(settlement);

            HashMap<String, Object> result = new HashMap<>(1);
            result.put("money",money);
            return JsonStandard.success(result);
        } catch (BusinessException e) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,e.getMessage());
        }
    }

    @PostMapping("/cashiering/preferential")
    public JsonStandard getPreferentialAmount(@RequestBody SettlementDTO settlement){
        if (settlement.getCoupon() != null) {
            HashSet<String> set = new HashSet<>(settlement.getCoupon());
            if(set.size() != settlement.getCoupon().size()){
                return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"有重复的优惠券");
            }
        }
        try {
            BigDecimal preferentialAmount = cashieringService.getPreferentialAmount(settlement);

            HashMap<String, Object> result = new HashMap<>(1);
            result.put("preferentialAmount",preferentialAmount);
            return JsonStandard.success(result);
        } catch (BusinessException e) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,e.getMessage());
        }
    }

    @PostMapping("/cashiering/coupon/info")
    public JsonStandard getCouponInfo(String couponCode){
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if (coupon == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"无此优惠券");
        }
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("coupon",coupon);
        return JsonStandard.success(map);
    }


}
