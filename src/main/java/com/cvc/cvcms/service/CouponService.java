package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.UserAndCoupon;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author nwer
 */
@Service
public interface CouponService {
    /**
     * 添加优惠券
     * @param coupon
     * @return
     */
    boolean addCoupon(Coupon coupon);

    /**
     *
     * @param description
     * @param startTime
     * @param endTime
     * @param isLimited
     * @param startPrice
     * @param decresedPrice
     * @return
     */
    Coupon createCoupon(String couponCode,
                        String description,
                         Date startTime,
                         Date endTime,
                         Integer isLimited,
                         BigDecimal startPrice,
                         BigDecimal decresedPrice);

    /**
     * 通过ID删除优惠券
     * @param couponId
     * @return
     */
    boolean deleteCouponById(Integer couponId);

    /**
     * 通过Code删除优惠券
     * @param couponCode
     * @return
     */
    boolean deleteCouponByCode(String couponCode);

    /**
     *查询所有优惠券
     * @return
     */
    List<Coupon> findAllCoupon();

    /**
     * 更新优惠券信息
     * @param coupon
     * @return
     */
    boolean updateCoupon(Coupon coupon);

    /**
     * 通过ID查询优惠券
     * @param id
     * @return
     */
    Coupon getCouponById(Integer id);

    /**
     * 通过Code查询优惠券
     * @param code
     * @return
     */
    Coupon getCouponByCode(String code);

    /**
     * 通过用户名和优惠券ID查询优惠券数量
     * @param username
     * @param couponId
     * @return
     */
    Integer hasCouponQuantity(String username,Integer couponId);

    /**
     * 通过优惠券ID查询可用商品的条形码列表
     * @param couponId
     * @return
     */
    List<String> getLimitGoodsBarCode(Integer couponId);

    /**
     * 通过用户名和优惠券ID删除用户的优惠券
     * @param username
     * @param couponId
     * @return
     */
    boolean deleteUserCoupon(String username,Integer couponId);

    /**
     * 通过用户名和优惠券ID 优惠券数量减一
     * @param username
     * @param couponId
     * @return
     */
    boolean reduceUserCoupon(String username,Integer couponId);

    /**
     * 通过用户名查询用户拥有的优惠券
     * @param userName
     * @return
     */
    List<Coupon> getCouponByUser(String userName);

    /**
     * 用户领取优惠券
     * @param userName
     * @param couponId
     * @param quantity
     * @return
     */
    boolean userClaimCoupon(String userName,Integer couponId, Integer quantity);

    /**
     * 查询用户是否拥有此优惠券
     * @param userName
     * @param couponCode
     * @param quantity
     * @return
     */
    UserAndCoupon getUserAndCoupon(String userName, String couponCode, Integer quantity);

    /**
     *
     * @param userName
     * @param couponCode
     * @param quantity
     * @return
     */
    boolean addUserAndCoupon(String userName,String couponCode, Integer quantity);

}
