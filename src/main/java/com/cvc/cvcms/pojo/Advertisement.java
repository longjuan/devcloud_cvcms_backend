package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 22:19
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Advertisement {
    private Integer id;
    private String img;
    private String detail;
    private Date startTime;
    private Date endTime;
    private Integer status;
}
