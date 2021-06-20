package com.cvc.cvcms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 21:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserStaffDTO {
    private Integer id;
    private String username;
    private boolean frozen;
    private String name;
    private Integer gender;
    private String phone;
    private Date entryTime;
    private String roleName;
    private String description;
}
