package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.FtpUtil;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.common.SystemHardwareInfo;
import com.cvc.cvcms.dao.SalesDao;
import com.cvc.cvcms.pojo.dto.UserStaffDTO;
import com.cvc.cvcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XiuGitHung
 * @Date: 2021/3/31 23:15
 * @Description:
 */

@RestController
public class CommonController {

    @Autowired
    UserService userService;

    @Autowired
    SalesDao salesDao;

    @Autowired
    FtpUtil ftpUtil;

    @GetMapping("/common/info")
    public JsonStandard info(){
        Map<String, Object> result = new HashMap<>(1);
        int orderNum = salesDao.getSalesRecords();
        BigDecimal money = userService.getMoney();
        BigDecimal Ymoney = userService.getYesterdayMoney();
        result.put("userNums",userService.getAll());
        result.put("goodsOnSale",userService.getGoodsOnSale());
        result.put("money",money==null? 0 : money);
        result.put("Ymoney",Ymoney==null? 0 : Ymoney);
        result.put("complain",userService.getComplain());
        result.put("orderNum",orderNum);
        return JsonStandard.success("success",result);
    }

    @GetMapping("/static_source/images/{filename}")
    public void getImg(HttpServletResponse resp, @PathVariable("filename") String filename) throws IOException {
        ftpUtil.DownloadFromFtp(filename,resp);
    }

    @PostMapping("/common/isMember")
    public JsonStandard isMember(String username){
        Map<String,Object> map = new HashMap<>(1);
        if(userService.getMemberByName(username) == null){
            map.put("flag",0);
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"没有此用户",map);
        }
        map.put("flag",1);
        return JsonStandard.success("success",map);

    }

    @GetMapping("/systemHardwareInfo")
    public JsonStandard systemHardwareInfo(){
        HashMap<String, Object> map = new HashMap<>(1);
        try {
            map.put("systemHardwareInfo",new SystemHardwareInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonStandard.success(map);
    }
}
