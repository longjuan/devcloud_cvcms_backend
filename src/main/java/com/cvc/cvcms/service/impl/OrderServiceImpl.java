package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.GoodsDao;
import com.cvc.cvcms.dao.OrderDao;
import com.cvc.cvcms.pojo.Goods;
import com.cvc.cvcms.pojo.Order;
import com.cvc.cvcms.pojo.OrderToGoods;
import com.cvc.cvcms.pojo.dto.NewOrderDTO;
import com.cvc.cvcms.pojo.dto.OrderDTO;
import com.cvc.cvcms.pojo.dto.OrderInfoDTO;
import com.cvc.cvcms.pojo.dto.OrdersDTO;
import com.cvc.cvcms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 19:32
 * @Description:
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    GoodsDao goodsDao;


    @Override
    public boolean createOrder(NewOrderDTO NewOrder) {
        Order order = new Order(null,NewOrder.getOrderDate(),null,null,NewOrder.getAmount(),"1", NewOrder.getSupplierId());
        if(orderDao.saveOrder(order) > 0){
            for (OrdersDTO ordersDTO : NewOrder.getOrdersList()) {
                orderDao.saveOrderAndGoods(new OrderToGoods(order.getId(),ordersDTO.getGoodsId(),ordersDTO.getQuantity()));
            }
            return true;
        }
        return false;
    }

    @Override
    public OrderDTO getOrderById(Integer id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        return orderDao.getAllOrder();
    }


    @Override
    public boolean updateStatus(Integer id, String status,Date date) {
        if(status.equals("1")) return orderDao.updateStatus(id,"2",date) > 0;
        if(status.equals("2")){
            if(orderDao.updateStatus(id,"3",date) > 0){
                OrderToGoods og = orderDao.getOrderAndGoods(id);
                Goods goods = goodsDao.findGoodsById(og.getGoodsId());
                goods.setStock(goods.getStock().add(og.getQuantity()));
                if(goodsDao.updateGoods(goods) > 0) return true;
            }
        }
        return false;
    }

    @Override
    public List<OrderInfoDTO> getOrderInfoById(Integer id) {
        List<OrderInfoDTO> list = orderDao.getOrderInfoById(id);
        return list;
    }

}
