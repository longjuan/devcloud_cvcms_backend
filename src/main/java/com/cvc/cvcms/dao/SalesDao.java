package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.SalesCoupon;
import com.cvc.cvcms.pojo.SalesItem;
import com.cvc.cvcms.pojo.SalesRecord;
import com.cvc.cvcms.pojo.SalesRecordToItem;
import com.cvc.cvcms.pojo.dto.SalesCouponAndCodeDTO;
import com.cvc.cvcms.pojo.dto.SalesRecordAndUsernameDTO;
import com.cvc.cvcms.pojo.dto.StatisticsClassificationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/29 13:00
 * @desc
 */
@Repository
@Mapper
public interface SalesDao {
    /**
     * 保存SalesRecord
     * @param salesRecord
     * @return
     */
    int saveSalesRecord(SalesRecord salesRecord);

    /**
     * 保存SalesItem
     * @param salesItem
     * @return
     */
    int saveSalesItem(SalesItem salesItem);

    /**
     * 保存SalesRecordToItem
     * @param salesRecordToItem
     * @return
     */
    int saveSalesRecordToItem(SalesRecordToItem salesRecordToItem);

    /**
     * 保存SalesCoupon
     * @param salesCoupon
     * @return
     */
    int saveSalesCoupon(SalesCoupon salesCoupon);

    /**
     * 获取SalesRecord记录
     * @param start
     * @param end
     * @param offset
     * @param size
     * @return
     */
    List<SalesRecordAndUsernameDTO> getSalesRecordsAndUsername(Date start, Date end, int offset, int size);

    /**
     * 获取SalesRecord记录数目
     * @param start
     * @param end
     * @return
     */
    int getSalesRecordNum(Date start, Date end);

    /**
     * 获取指定时间的总金额
     * @param start
     * @param end
     * @return
     */
    BigDecimal getTurnover(Date start, Date end);

    /**
     * 获取分类的销售额数据
     * @param start
     * @param end
     * @return
     */
    List<StatisticsClassificationDTO> getStatisticsClassification(Date start, Date end);

    /**
     * 通过用户id查找销售记录
     * @param userId
     * @return
     */
    List<SalesRecord> getSalesRecordsByUserId(Integer userId, int offset, int size);

    /**
     * 通过用户id查找销售记录数量
     * @param userId
     * @return
     */
    int getNumOfSalesRecordsByUserId(Integer userId);

    /**
     * 根据id获取销售记录
     * @param id
     * @return
     */
    SalesRecordAndUsernameDTO getSalesRecordAndUsernameDto(Integer id);

    /**
     * 根据销售记录id获取销售项
     * @param salesRecordId
     * @return
     */
    List<SalesItem> getSalesItems(Integer salesRecordId);

    /**
     * 根据销售记录id获取使用的优惠券
     * @param salesRecordId
     * @return
     */
    List<SalesCoupon> getSalesCoupons(Integer salesRecordId);

    /**
     * 根据销售记录id获取使用的优惠券和优惠券码
     * @param: [salesRecordId]
     * @return: java.util.List<SalesCouponDTO>
     */
    List<SalesCouponAndCodeDTO> getSalesCouponsDTO(Integer salesRecordId);

    /**
     * 获取当天的销售记录数量
     * @param: []
     * @return: int
     */
    int getSalesRecords();
}
