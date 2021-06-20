package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.UserAndCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/29 20:53
 * @desc
 */
@Repository
@Mapper
public interface CouponDao {
    /**
     * 添加优惠卷
     * @param coupon
     * @return
     */
    int addCoupon(Coupon coupon);

    /**
     * 通过ID删除优惠卷
     * @param couponId
     * @return
     */
    int deleteCouponById(Integer couponId);

    /**
     * 通过Code删除优惠卷
     * @param code
     * @return
     */
    int deleteCouponByCode(String code);

    /**
     * 更新优惠卷信息
     * @param coupon
     * @return
     */
    int updateCoupon(Coupon coupon);

    /**
     * 获得所有优惠卷
     * @return
     */
    List<Coupon> findAllCoupon();

    /**
     * 通过id获得优惠券
     * @return
     */
    public Coupon getCouponById(Integer id);

    /**
     * 通过优惠券code拿到优惠券
     * @param code
     * @return
     */
    public Coupon getCouponByCode(String code);

    /**
     * 根据username获取优惠券的拥有数量
     * @param username
     * @param couponId
     * @return
     */
    public Integer hasCouponQuantity(String username,Integer couponId);

    /**
     * 根据券id获得券适用的商品条形码
     * @param couponId
     * @return 商品条形码
     */
    public List<String> getLimitGoodsBarCode(Integer couponId);

    /**
     * 删除用户的券
     * @param username
     * @param couponId
     * @return
     */
    public int deleteUserCoupon(String username,Integer couponId);

    /**
     * 减少用户的券
     * @param username
     * @param couponId
     * @return
     */
    public int reduceUserCoupon(String username,Integer couponId);

    /**
     * 查询用户拥有的优惠券
     * @param userName
     * @return
     */
    public List<Coupon> getCouponByUser(String userName);

    /**
     * 用户领取优惠券
     * @param userName
     * @param couponId
     * @param quantity
     * @return
     */
    public int UserClaimCoupon(String userName,Integer couponId, Integer quantity);

    /**
     * 查询用户是否拥有此优惠券
     * @param userName
     * @param couponId
     * @param quantity
     * @return
     */
    public UserAndCoupon getUserAndCoupon(String userName, Integer couponId, Integer quantity);

    /**
     * 新增用户拥有的优惠券
     * @param userName
     * @param couponId
     * @param quantity
     * @return
     */
    public int addUserAndCoupon(String userName, Integer couponId, Integer quantity);

}
