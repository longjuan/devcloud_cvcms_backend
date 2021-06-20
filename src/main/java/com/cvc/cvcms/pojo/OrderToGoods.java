package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/3 20:01
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderToGoods {
    private Integer orderId;
    private Integer goodsId;
    private BigDecimal quantity;
}
