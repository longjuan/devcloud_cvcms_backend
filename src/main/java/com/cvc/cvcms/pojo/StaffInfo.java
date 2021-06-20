package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ZZJ
 * @date 2021/4/1 18:11
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class StaffInfo {
    private Integer userId;
    private String name;
    private Integer gender;
    private String phone;
    private Date entryTime;
    private String picture;

}
