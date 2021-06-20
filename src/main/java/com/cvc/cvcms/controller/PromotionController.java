package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.dao.GoodsDao;
import com.cvc.cvcms.pojo.Promotion;
import com.cvc.cvcms.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 11:29
 * @Description:
 */
@RestController
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @Autowired
    GoodsDao goodsDao;

    @PostMapping("/promotion/update")
    public JsonStandard update(Integer goodsId, @RequestParam(required = false) BigDecimal promotionPrice,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) Date startTime,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) Date endTime){
        if(promotionService.findPromotionById(goodsId) != null){
            if(promotionService.updatePromotion(new Promotion(null,goodsId,promotionPrice,startTime,endTime))){
                return JsonStandard.success("修改成功");
            }
        }else{
            if(promotionService.addPromotion(new Promotion(null,goodsId,promotionPrice,startTime,endTime))){
                return JsonStandard.success("新增成功");
            }
        }
        return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"error");
    }
}
