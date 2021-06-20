package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Order;
import com.cvc.cvcms.pojo.dto.NewOrderDTO;
import com.cvc.cvcms.pojo.dto.OrderDTO;
import com.cvc.cvcms.pojo.dto.OrderInfoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 19:29
 * @Description:
 */
@Service
public interface OrderService {

    /**
     * 新建采购订单(新建订单和订单商品关系)
     * @param: [orderDate, payDate, acceptanceDate, accepted, pay, amount, status, supplierId, orderId, goodsId, quantity]
     * @return: boolean
     */
    boolean createOrder(NewOrderDTO NewOrder);


    /**
     * 根据id获取采购订单
     * @param: [id]
     * @return: int
     */
    OrderDTO getOrderById(Integer id);

    /**
     * 获取所有采购订单
     * @param: [id]
     * @return: int
     */
    List<OrderDTO> getAllOrder();

    /**
     * 根据id以及status修改采购订单状态
     * @param: [id, status]
     * @return: int
     */
    boolean updateStatus(Integer id,String status,Date date);

    /**
     * 根据id获取采购订单的信息 包括商品名，单价，数量
     * @param: [id]
     * @return: java.util.List<com.cvc.cvcms.pojo.dto.OrderInfoDTO>
     */
    List<OrderInfoDTO> getOrderInfoById(Integer id);
}
