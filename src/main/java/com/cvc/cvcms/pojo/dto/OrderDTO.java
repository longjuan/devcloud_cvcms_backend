package com.cvc.cvcms.pojo.dto;

import com.cvc.cvcms.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 * @date 2021/4/16 14:55
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderDTO extends Order {
    private String name;
}
