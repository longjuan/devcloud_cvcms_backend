package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ZZJ
 * @date 2021/4/3 17:39
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class StatisticsClassificationDTO {
    private BigDecimal amount;
    private String classification;
}
