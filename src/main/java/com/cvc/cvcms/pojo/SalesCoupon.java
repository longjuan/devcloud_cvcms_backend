package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ZZJ
 * @date 2021/3/30 12:57
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesCoupon {
    private Integer saleId;
    private Integer couponId;
    private BigDecimal decresedPrice;
}
