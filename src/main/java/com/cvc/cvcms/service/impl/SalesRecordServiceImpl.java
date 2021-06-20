package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.SalesDao;
import com.cvc.cvcms.pojo.SalesCoupon;
import com.cvc.cvcms.pojo.SalesItem;
import com.cvc.cvcms.pojo.SalesRecord;
import com.cvc.cvcms.pojo.dto.SalesCouponAndCodeDTO;
import com.cvc.cvcms.pojo.dto.SalesRecordAndUsernameDTO;
import com.cvc.cvcms.pojo.dto.StatisticsClassificationDTO;
import com.cvc.cvcms.service.SalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ZZJ
 * @date 2021/3/31 19:27
 * @desc
 */
@Service
@Transactional
public class SalesRecordServiceImpl implements SalesRecordService {
    @Autowired
    private SalesDao salesDao;

    @Override
    public List<SalesRecordAndUsernameDTO> getSalesRecordsAndUsername(Date date, Integer page, Integer pagesize) {
        Date end = getOneDayEndTime(date);
        return salesDao.getSalesRecordsAndUsername(date,end,(page-1)*pagesize,pagesize);

    }

    /**
     * 获取date+1天的时间
     * @param date
     * @return
     */
    private Date getOneDayEndTime(Date date){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,1);
            return calendar.getTime();
        }
        return null;
    }

    @Override
    public int getSalesRecordNum(Date date) {
        Date end = getOneDayEndTime(date);
        return salesDao.getSalesRecordNum(date,end);
    }

    @Override
    public BigDecimal[] statisticsTurnover() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -dayOfWeek-6);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal[] turnover = new BigDecimal[14];
        for (int i = 0; i < 14; i++) {
            Date start = calendar.getTime();
            calendar.add(Calendar.DATE,1);
            Date end = calendar.getTime();
            BigDecimal money = salesDao.getTurnover(start, end);
            turnover[i] = money == null? new BigDecimal("0") : money;
        }
        return turnover;
    }

    @Override
    public List<StatisticsClassificationDTO> statisticsClassification(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-day);
        List<StatisticsClassificationDTO> list = salesDao.getStatisticsClassification(calendar.getTime(), new Date());
        return list;
    }

    @Override
    public List<SalesRecord> getSalesRecords(Integer userId,Integer page,Integer pageSize) {
        return salesDao.getSalesRecordsByUserId(userId,(page-1)*pageSize,pageSize);
    }

    @Override
    public int getNumOfSalesRecords(Integer userId) {
        return salesDao.getNumOfSalesRecordsByUserId(userId);
    }

    @Override
    public SalesRecordAndUsernameDTO getSalesRecordAndUsernameDto(Integer id) {
        return salesDao.getSalesRecordAndUsernameDto(id);
    }

    @Override
    public List<SalesItem> getSalesItems(Integer salesRecordId) {
        return salesDao.getSalesItems(salesRecordId);
    }

    @Override
    public List<SalesCoupon> getSalesCoupons(Integer salesRecordId) {
        return salesDao.getSalesCoupons(salesRecordId);
    }

    @Override
    public List<SalesCouponAndCodeDTO> getSalesCouponsDTO(Integer salesRecordId) {
        return salesDao.getSalesCouponsDTO(salesRecordId);
    }

    @Override
    public int getSalesRecords() {
        return salesDao.getSalesRecords();
    }
}
