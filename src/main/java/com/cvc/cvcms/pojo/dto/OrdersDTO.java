package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: XiuGitHung
 * @Date: 2021/6/4 12:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrdersDTO {
    private Integer goodsId;
    private BigDecimal quantity;
}
