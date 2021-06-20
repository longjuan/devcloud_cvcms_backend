package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 * @date 2021/4/7 16:56
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MemberInfo {
    private String username;
    private Integer points;
    private String phone;

}
