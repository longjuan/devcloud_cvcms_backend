package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: XiuGitHung
 * @Date: 2021/6/4 17:49
 * @Description:
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {
    private String goodsName;
    private BigDecimal price;
    private BigDecimal quantity;
}
