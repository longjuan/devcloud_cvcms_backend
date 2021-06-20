package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZZJ
 * @date 2021/3/29 20:53
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Coupon {
    private Integer id;
    private String couponCode;
    private String description;
    private Date startTime;
    private Date endTime;
    private Boolean limited;
    private BigDecimal startPrice;
    private BigDecimal decresedPrice;
    private Integer status;
}
