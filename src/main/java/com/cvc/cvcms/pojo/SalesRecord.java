package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZZJ
 * @date 2021/3/30 12:49
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesRecord {
    private Integer id;
    private Integer memberId;
    private Date date;
    private BigDecimal amount;

}
