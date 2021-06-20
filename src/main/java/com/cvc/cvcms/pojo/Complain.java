package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/9 8:58
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
public class Complain {

    private Integer id;
    private Integer userid;
    private String info;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")private Date date;
    private String status;
    private String pic;

}
