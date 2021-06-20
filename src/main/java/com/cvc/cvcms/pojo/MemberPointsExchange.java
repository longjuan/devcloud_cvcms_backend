package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ZZJ
 * @date 2021/4/7 16:57
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MemberPointsExchange {
    private String username;
    private String content;
    private Integer points;
    private Date date;

}
