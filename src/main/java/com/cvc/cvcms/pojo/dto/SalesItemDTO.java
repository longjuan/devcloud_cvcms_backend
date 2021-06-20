package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ZZJ
 * @date 2021/3/29 19:23
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesItemDTO {
    private String barCode;
    private BigDecimal quantity;

}
