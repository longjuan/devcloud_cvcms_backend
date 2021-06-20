package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.SalesCoupon;
import com.cvc.cvcms.pojo.SalesItem;
import com.cvc.cvcms.pojo.SalesRecord;
import com.cvc.cvcms.pojo.dto.SalesCouponAndCodeDTO;
import com.cvc.cvcms.pojo.dto.SalesRecordAndUsernameDTO;
import com.cvc.cvcms.pojo.dto.StatisticsClassificationDTO;
import com.cvc.cvcms.service.SalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/31 17:11
 * @desc
 */
@RestController
public class SalesRecordController {
    @Autowired
    private SalesRecordService salesRecordService;

    @PostMapping("/salesRecord")
    public JsonStandard salesRecord(@DateTimeFormat(pattern="yyyy-MM-dd")Date date,Integer page,Integer pagesize){
        List<SalesRecordAndUsernameDTO> salesRecords = salesRecordService.getSalesRecordsAndUsername(date, page, pagesize);
        int salesRecordNum = salesRecordService.getSalesRecordNum(date);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("salesRecords",salesRecords);
        map.put("num",salesRecordNum);
        return JsonStandard.success(map);
    }

    @GetMapping("/goods/statistics/turnover")
    public JsonStandard goodsStatisticsTurnover(){
        Map<String, Object> map = new HashMap<String, Object>(1);
        BigDecimal[] turnover = salesRecordService.statisticsTurnover();
        map.put("turnover",turnover);
        return JsonStandard.success(map);
    }

    @PostMapping("/goods/statistics/classification")
    public JsonStandard goodsStatisticsClassification(Integer day){
        if (day == null){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"天数不能为空");
        }
        List<StatisticsClassificationDTO> list = salesRecordService.statisticsClassification(day);
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("result",list);
        return JsonStandard.success(map);
    }

    @PostMapping("/salesRecord/detailed")
    public JsonStandard salesRecordDetailed(Integer id){
        SalesRecordAndUsernameDTO salesRecord = salesRecordService.getSalesRecordAndUsernameDto(id);
        List<SalesItem> salesItems = salesRecordService.getSalesItems(id);
        List<SalesCouponAndCodeDTO> salesCoupons = salesRecordService.getSalesCouponsDTO(id);
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("salesRecord",salesRecord);
        map.put("salesItems",salesItems);
        map.put("salesCoupons",salesCoupons);
        return JsonStandard.success(map);
    }
}
