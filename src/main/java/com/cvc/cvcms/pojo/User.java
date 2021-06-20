package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/24 14:12
 * @desc
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean frozen;
    private String roleName;

    public User(UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.frozen = userDetails.isEnabled();
    }
}
