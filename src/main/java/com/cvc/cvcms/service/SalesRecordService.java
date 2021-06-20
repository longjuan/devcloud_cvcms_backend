package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.SalesCoupon;
import com.cvc.cvcms.pojo.SalesItem;
import com.cvc.cvcms.pojo.SalesRecord;
import com.cvc.cvcms.pojo.dto.SalesCouponAndCodeDTO;
import com.cvc.cvcms.pojo.dto.SalesRecordAndUsernameDTO;
import com.cvc.cvcms.pojo.dto.StatisticsClassificationDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/31 19:20
 * @desc
 */
@Service
public interface SalesRecordService {
    /**
     * 获取销售记录
     * @param date
     * @param page
     * @param pagesize
     * @return
     */
    List<SalesRecordAndUsernameDTO> getSalesRecordsAndUsername(Date date, Integer page, Integer pagesize);

    /**
     * 获取记录数
     * @param date
     * @return
     */
    int getSalesRecordNum(Date date);

    /**
     * 统计销售额
     * @return
     */
    BigDecimal[] statisticsTurnover();

    /**
     * 获取分类的销售额数据
     * @param day
     * @return
     */
    List<StatisticsClassificationDTO> statisticsClassification(int day);

    /**
     * 通过userid寻找销售记录
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    List<SalesRecord> getSalesRecords(Integer userId,Integer page,Integer pageSize);

    /**
     * 通过userid寻找销售记录数量
     * @param userId
     * @return
     */
    int getNumOfSalesRecords(Integer userId);

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
