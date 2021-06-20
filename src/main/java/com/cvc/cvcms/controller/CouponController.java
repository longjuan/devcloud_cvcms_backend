package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.pojo.UserAndCoupon;
import com.cvc.cvcms.service.CouponService;
import com.cvc.cvcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.cvc.cvcms.common.CouponCodeUtil.getCouponCode;

/**
 * @author nwer
 */

@RestController
public class CouponController {
    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @PostMapping("/coupon/add")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard add(@RequestParam(required = false) String description,
                            @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,
                            @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date endTime,
                            @RequestParam(required = false) Integer isLimited,
                            @RequestParam(required = false) BigDecimal startPrice,
                            @RequestParam(required = false) BigDecimal decresedPrice){

        String couponCode = getCouponCode();
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if(coupon != null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"已存在该优惠卷");
        }
        coupon = couponService.createCoupon(couponCode ,description,startTime,endTime,isLimited ,startPrice,decresedPrice);

        boolean flag = couponService.addCoupon(coupon);
        if(flag) {
            return JsonStandard.success("添加成功");
        }

        return JsonStandard.error( ErrorEnum.NOT_ACCEPTABLE,"添加失败");
    }

    @PostMapping("/coupon/delete")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard delete(String couponCode){
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if (coupon == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"无此优惠券");
        }
        couponService.deleteCouponByCode(couponCode);
        return JsonStandard.success("删除成功");



    }

    @PostMapping("/coupon/update")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard update(String couponCode,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date startTime,
                               @RequestParam(required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date endTime,
                               @RequestParam(required = false) Integer isLimited,
                               @RequestParam(required = false) BigDecimal startPrice,
                               @RequestParam(required = false) BigDecimal decresedPrice,
                               @RequestParam(required = false) Integer status) throws IOException {
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if (coupon == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"无此优惠券");
        }

        if(description != null) {
            coupon.setDescription(description);
        }
        if(startTime != null) {
            coupon.setStartTime(startTime);
        }
        if(endTime != null) {
            coupon.setEndTime(endTime);
        }
        if(isLimited != null) {
            if (isLimited == 1){
                coupon.setLimited(true);
            } else if(isLimited == 0){
                coupon.setLimited(false);
            }
        }
        if(startPrice != null) {
            coupon.setStartPrice(startPrice);
        }
        if(decresedPrice != null) {
            coupon.setDecresedPrice(decresedPrice);
        }
        if(status != null) {
            coupon.setStatus(status);
        }


        couponService.updateCoupon(coupon);
        return JsonStandard.success("更改成功");
    }

    @PostMapping("/coupon/get")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getCouponByCode(String  couponCode) {
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if (coupon == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"无此优惠券");
        }
        Map<String, Object> result = new HashMap<>(1);
        result.put("coupon", coupon);
        return JsonStandard.success(result);
    }

    @GetMapping("/coupons")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getAllCoupon() {
        List<Coupon> couponList = couponService.findAllCoupon();
        Map<String, Object> result = new HashMap<>(1);
        result.put("coupons",couponList);
        return JsonStandard.success(result);
    }

    @PostMapping("/coupon/user")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getCouponByUser(String userName) {
        User user = userService.getUserByUsername(userName);
        if(user == null) return JsonStandard.error(ErrorEnum.NOT_FOUND,"无此用户");
        List<Coupon> couponList = couponService.getCouponByUser(userName);
        if(couponList == null) return JsonStandard.success("尚未拥有优惠券");

        Map<String, Object> result = new HashMap<>(1);
        result.put("coupons",couponList);
        return JsonStandard.success(result);
    }

    @PostMapping("/coupon/userClaim")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard userClainCoupon(String userName, String couponCode, Integer quantity) {
        User user = userService.getUserByUsername(userName);
        if(user == null) return JsonStandard.error(ErrorEnum.NOT_FOUND,"无此用户");
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if(coupon == null){return JsonStandard.error(ErrorEnum.NOT_FOUND,"无此优惠券"); }
        UserAndCoupon userAndCoupon = couponService.getUserAndCoupon(userName,couponCode,quantity);
        boolean flag = false;
        if(userAndCoupon == null){
            flag = couponService.addUserAndCoupon(userName,couponCode,quantity);
        }else{
            flag = couponService.userClaimCoupon(userName, coupon.getId(), quantity);
        }
        if(flag){
            return JsonStandard.success("添加成功");
        }else{
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"添加失败");
        }

    }





}
