package com.cvc.cvcms.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author ZZJ
 * @date 2021/3/24 15:16
 * @desc
 */
@SpringBootTest
class MyDataUserDetailsServiceImplTest {
    @Autowired
    private UserDetailsService myDataUserDetailsService;
    @Test
    void loadUserByUsernameTest(){
        myDataUserDetailsService.loadUserByUsername("admin");
    }

}