package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.SalesRecord;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.service.SalesRecordService;
import com.cvc.cvcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/5 19:19
 * @desc
 */
@RestController
public class CustomerController {
    @Autowired
    private UserService userService;
    @Autowired
    private SalesRecordService salesRecordService;

    @PostMapping("/wechat/record")
    public JsonStandard getRecords(String username,Integer page,Integer pageSize){
        // TODO 到时候删除username 根据token获取
        User user = userService.getUserByUsername(username);
        List<SalesRecord> salesRecords = salesRecordService.getSalesRecords(user.getId(),page,pageSize);
        int numOfSalesRecords = salesRecordService.getNumOfSalesRecords(user.getId());
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("record",salesRecords);
        map.put("num",numOfSalesRecords);
        return JsonStandard.success(map);
    }


}
