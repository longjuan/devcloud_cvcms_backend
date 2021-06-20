package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 1:06
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Order {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date orderDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date warehousingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  private Date acceptanceDate;
    private BigDecimal amount;
    private String status;
    private Integer supplierId;
}
