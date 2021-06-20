package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 11:11
 * @Description:
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private Integer id;
    private Integer goodsId;
    private BigDecimal promotionPrice;
    private Date startTime;
    private Date endTime;
}
