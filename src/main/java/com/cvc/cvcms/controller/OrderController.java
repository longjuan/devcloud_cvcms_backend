package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.dao.OrderDao;
import com.cvc.cvcms.pojo.Order;
import com.cvc.cvcms.pojo.dto.NewOrderDTO;
import com.cvc.cvcms.pojo.dto.OrderDTO;
import com.cvc.cvcms.pojo.dto.OrderInfoDTO;
import com.cvc.cvcms.pojo.dto.OrdersDTO;
import com.cvc.cvcms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 19:35
 * @Description:
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/add")
    public JsonStandard add(@RequestBody NewOrderDTO NewOrder){
        if(orderService.createOrder(NewOrder)){
            return JsonStandard.success("新增成功");
        }
        return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"新增失败");
    }


    @PostMapping("/order/get")
    public JsonStandard get(Integer id){
        Map<String, Object> map = new HashMap<>(1);
        if (id != null){
            OrderDTO order = orderService.getOrderById(id);
            map.put("orders",order);
            return JsonStandard.success("success",map);
        }else{
            List<OrderDTO> list = orderService.getAllOrder();
            map.put("orders",list);
            return JsonStandard.success("success",map);
        }
    }

    @PostMapping("/order/updateStatus")
    public JsonStandard updateStatus(Integer id,String status,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date date){
        return orderService.updateStatus(id,status,date)?JsonStandard.success("success"):JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"修改异常");
    }

    @PostMapping("/order/getOrderInfo")
    public JsonStandard getOrderInfo(Integer id){
        List<OrderInfoDTO> list = orderService.getOrderInfoById(id);
        Map<String,Object> result = new HashMap<>();
        result.put("OrderInfo",list);
        return JsonStandard.success("成功",result);
    }
}
