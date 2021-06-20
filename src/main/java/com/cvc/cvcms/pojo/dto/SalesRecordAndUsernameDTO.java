package com.cvc.cvcms.pojo.dto;

import com.cvc.cvcms.pojo.SalesRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZZJ
 * @date 2021/4/14 18:39
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SalesRecordAndUsernameDTO extends SalesRecord {
    private String username;
}
