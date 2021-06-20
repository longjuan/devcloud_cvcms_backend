package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.Const;
import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Goods;
import com.cvc.cvcms.service.CashieringService;
import com.cvc.cvcms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiuGitHung
 * @Date: 2021/3/28 22:31
 * @Description:
 */
@RestController
public class GoodsManageController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    private CashieringService cashieringService;


    @PostMapping("/product/add")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard add(Goods goods){
        if (!StringUtils.hasText(goods.getBarCode())){
            goods.setBarCode(randomBarcode());
        }
       return goodsService.addGood(goods)?JsonStandard.success("新增成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"新增异常");
    }

    @PostMapping("/product/delete")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard delete(Integer goodsId){
        String msg = goodsService.deleteGoods(goodsId);
        if(msg == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this goods");
        }
        return JsonStandard.success(msg);
    }

    @PostMapping("/product/update")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard update(Integer id,
                               String goodsName, BigDecimal price,
                               BigDecimal stock, BigDecimal stockWarning,
                               Integer stockDecimal, String barCode,
                               Integer onSale,Integer supplierId,
                               String stockUnit,String classification){

        if(goodsService.updateGoods(new Goods(id,goodsName,price,stock,stockWarning,stockDecimal,barCode,onSale,supplierId,stockUnit,classification))){
            return JsonStandard.success("更改成功");
        }
        return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"更改异常");
    }

    @PostMapping("/product/get")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard get(@RequestParam(required = false) String goodsName,
                            @RequestParam(required = false) String classification,
                            @RequestParam(required = false) Integer onSale,
                            Integer page,Integer pagesize){
        List<Goods> goodsList = goodsService.getGoodsByConditions(goodsName, classification, onSale, page, pagesize);
        int total = goodsService.getNumOfFindGoodsByConditions(goodsName, classification, onSale);
        Map<String, Object> result = new HashMap<>(2);
        result.put("goods",goodsList);
        result.put("total",total);
        return JsonStandard.success(result);
    }

    @PostMapping("/product/up")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard up(Integer id){
        String flag = goodsService.GoodsUp(id);
        if(flag == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this goods");
        }
        return JsonStandard.success(flag);
    }

    @PostMapping("/product/down")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard down(Integer id){
        String flag = goodsService.GoodsDown(id);
        if(flag == null) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this goods");
        }
        return JsonStandard.success(flag);
    }

    @GetMapping("/product/classification")
    //    @PreAuthorize("hasAuthority('')")
    public JsonStandard getClassification(){
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("classification", Const.CLASSIFICATION);
        return JsonStandard.success(map);
    }


    /**
     * 生成随机条形码
     * @return
     */
    private String randomBarcode(){
        String barcode;
        do {
            barcode = "69";
            barcode += (int)(Math.random()*3);
            for (int i = 0; i < 10; i++) {
                barcode += (int)(Math.random()*10);
            }
        }while (cashieringService.getPrice(barcode) != null);
        return barcode;
    }

    @GetMapping("/goods/supplier")
    public JsonStandard getGoodsBySupplierId(Integer supplierId){
        List<Goods> goodsBySupplierId = goodsService.getGoodsBySupplierId(supplierId);
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("goods", goodsBySupplierId);
        return JsonStandard.success(map);
    }

}
