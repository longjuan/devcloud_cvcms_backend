package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/29 20:06
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SettlementDTO {
    private List<SalesItemDTO> items;
    private String member;
    private List<String> coupon;
    private BigDecimal estimate;
}
