package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/15 16:09
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesCouponAndCodeDTO {
    private Integer saleId;
    private Integer couponId;
    private BigDecimal decresedPrice;
    private String couponCode;
}
