package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ZZJ
 * @date 2021/3/30 12:54
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesItem {
    private Integer id;
    private Integer goodsId;
    private String goodsName;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal subtotal;
}
