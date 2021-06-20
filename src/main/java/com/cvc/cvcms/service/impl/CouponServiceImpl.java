package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.CouponDao;
import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.UserAndCoupon;
import com.cvc.cvcms.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author nwer
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;

    @Autowired
    Coupon coupon;


    @Override
    public boolean addCoupon(Coupon coupon) {
        return couponDao.addCoupon(coupon) > 0;
    }

    @Override
    public Coupon createCoupon(String couponCode ,String description, Date startTime, Date endTime, Integer isLimited, BigDecimal startPrice, BigDecimal decresedPrice) {
        if(couponCode != null) {
            coupon.setCouponCode(couponCode);
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
        }else{
            coupon.setLimited(false);
        }
        if(startPrice != null) {
            coupon.setStartPrice(startPrice);
        }
        if(decresedPrice != null) {
            coupon.setDecresedPrice(decresedPrice);
        }

        return coupon;
    }

    @Override
    public boolean deleteCouponById(Integer couponId) {
        return couponDao.deleteCouponById(couponId) > 0;
    }

    @Override
    public boolean deleteCouponByCode(String couponCode) {
        return couponDao.deleteCouponByCode(couponCode) > 0;
    }


    @Override
    public List<Coupon> findAllCoupon() {
        return couponDao.findAllCoupon();
    }

    @Override
    public boolean updateCoupon(Coupon coupon) {
        return couponDao.updateCoupon(coupon) > 0;
    }

    @Override
    public Coupon getCouponById(Integer id) {
        return couponDao.getCouponById(id);
    }

    @Override
    public Coupon getCouponByCode(String code) {
        return couponDao.getCouponByCode(code);
    }

    @Override
    public Integer hasCouponQuantity(String username, Integer couponId) {
        return couponDao.hasCouponQuantity(username, couponId);
    }

    @Override
    public List<String> getLimitGoodsBarCode(Integer couponId) {
        return couponDao.getLimitGoodsBarCode(couponId);
    }

    @Override
    public boolean deleteUserCoupon(String username, Integer couponId) {
        return couponDao.deleteUserCoupon(username, couponId) > 0 ;
    }

    @Override
    public boolean reduceUserCoupon(String username, Integer couponId) {
        return couponDao.reduceUserCoupon(username, couponId) > 0;
    }

    @Override
    public List<Coupon> getCouponByUser(String userName) {
        return couponDao.getCouponByUser(userName);
    }

    @Override
    public boolean userClaimCoupon(String userName, Integer couponId, Integer quantity) {
        return couponDao.UserClaimCoupon(userName, couponId, quantity) > 0;
    }

    @Override
    public UserAndCoupon getUserAndCoupon(String userName, String couponCode, Integer quantity) {
        Coupon coupon = couponDao.getCouponByCode(couponCode);
        return couponDao.getUserAndCoupon(userName, coupon.getId(),quantity);
    }

    @Override
    public boolean addUserAndCoupon(String userName, String couponCode, Integer quantity) {
        Coupon coupon = couponDao.getCouponByCode(couponCode);
        return couponDao.addUserAndCoupon(userName, coupon.getId(),quantity) > 0;
    }
}
