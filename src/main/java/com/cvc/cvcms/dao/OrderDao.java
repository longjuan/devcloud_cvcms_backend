package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Order;
import com.cvc.cvcms.pojo.OrderToGoods;
import com.cvc.cvcms.pojo.dto.OrderDTO;
import com.cvc.cvcms.pojo.dto.OrderInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 1:12
 * @Description:
 */
@Mapper
@Repository
public interface OrderDao {

    /**
     * 保存订单
     * @param: [orderDate, payDate, acceptanceDate, accepted, pay, amount, status, supplierId]
     * @return: int
     */
    int saveOrder(Order order);

    /**
     * 保存商品订单对应关系
     * @param: [orderId, goodsId, quantity]
     * @return: int
     */
    int saveOrderAndGoods(OrderToGoods orderToGoods);

    /**
     * 查询商品订单对应关系
     * @param: [orderToGoods]
     * @return: int
     */
    OrderToGoods getOrderAndGoods(Integer orderId);

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
    int updateStatus(Integer id,String status,Date date);

    /**
     * 根据id获取采购订单的信息 包括商品名，单价，数量
     * @param: [id]
     * @return: java.util.List<com.cvc.cvcms.pojo.dto.OrderInfoDTO>
     */
    List<OrderInfoDTO> getOrderInfoById(Integer id);
}
