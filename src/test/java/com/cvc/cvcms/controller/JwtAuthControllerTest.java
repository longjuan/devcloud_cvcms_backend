package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.JsonStandard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ZZJ
 * @date 2021/3/24 15:03
 * @desc
 */
@SpringBootTest
class JwtAuthControllerTest {
    @Autowired
    private JwtAuthController jwtAuthController;

    @Test
    void getPasswordEncodingText(){
        String hashpw = BCrypt.hashpw(String.valueOf(123), BCrypt.gensalt());
        System.out.println(hashpw);
    }

    @Test
    void login(){
        JsonStandard jsonStandard = jwtAuthController.login("admin", "123");
        System.out.println(jsonStandard);
    }

}