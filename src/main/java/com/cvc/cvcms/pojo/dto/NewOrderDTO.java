package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/6/4 12:10
 * @Description:
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDTO {
    private Integer supplierId;
    private BigDecimal amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date orderDate;
    private List<OrdersDTO> ordersList;
}
