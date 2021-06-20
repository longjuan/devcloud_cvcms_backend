package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 20:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Role {
    private Integer id;
    private String roleName;
    private String description;
}
