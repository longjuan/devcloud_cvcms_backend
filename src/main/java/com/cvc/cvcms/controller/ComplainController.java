package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.pojo.Complain;
import com.cvc.cvcms.service.ComplainService;
import com.cvc.cvcms.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/9 9:11
 * @Description:
 */
@RestController
public class ComplainController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ComplainService complainService;

    @PostMapping("/complain/add")
    public JsonStandard add(String username, String info, String phone, Date date, String pic){
        Complain complain = new Complain();
        complain.setUserid(userService.getUserByUsername(username).getId());
        complain.setPic("https://gitee.com/gdut_deepcode/ImageServer/raw/master/master/img/20210617014717.jpg");
        complain.setStatus("待解决");
        complain.setPhone(phone);
        complain.setInfo(info);
        date = new Date();
        complain.setDate(date);
        return complainService.addComplain(complain)?JsonStandard.success("新增成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"新增异常");
    }

    @PostMapping("/complain/update")
    public JsonStandard update(Complain complain){
        return complainService.updateComplain(complain)?JsonStandard.success("修改成功"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"修改异常");
    }

    @PostMapping("/complain/get")
    public JsonStandard update(Integer userId){
        Map<String,Object> map = new HashMap<>(1);
        if(userId == null){
            map.put("complains",complainService.getAll());
            return JsonStandard.success("success",map);
        }else{
            Complain complain = complainService.getComplainById(userId);
            map.put("complain",complain);
            return complain == null?JsonStandard.success("success",map):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this complain");
        }
    }
}
