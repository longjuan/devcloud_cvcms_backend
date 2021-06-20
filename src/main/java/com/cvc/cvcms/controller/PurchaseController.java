package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Supplier;
import com.cvc.cvcms.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 21:09
 * @Description:
 */
@RestController
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/purchase/addSup")
    public JsonStandard addSup(String name,String phone,String address,String remark){
        return purchaseService.saveSupplier(name,phone,address,remark)?JsonStandard.success("新增成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"新增异常");
    }

    @PostMapping("/purchase/getSup")
    public JsonStandard getSup(@RequestParam(required = false) String name){
        Map<String,Object> result = new HashMap<>(1);
        if(StringUtils.hasText(name)){
            Supplier supplier = purchaseService.getSupplierByName(name);
            if(supplier == null){
                return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this supplier");
            }
            result.put("suppliers",supplier);
            return JsonStandard.success("success",result);
        }
        List<Supplier> list = purchaseService.getAllSupplier();
        result.put("suppliers",list);
        return JsonStandard.success("success",result);
    }

    @PostMapping("/purchase/updateSup")
    public JsonStandard updateSup(Supplier supplier){
        return purchaseService.updateSupplier(supplier)?JsonStandard.success("修改成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"修改异常");
    }

    @PostMapping("/purchase/deleteSup")
    public JsonStandard deleteSup(Integer id){
        return purchaseService.deleteSupplier(id)?JsonStandard.success("删除成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"删除异常");
    }

}
