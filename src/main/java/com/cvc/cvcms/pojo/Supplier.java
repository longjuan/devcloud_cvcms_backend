package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 20:53
 * @Description:
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String remark;
}
