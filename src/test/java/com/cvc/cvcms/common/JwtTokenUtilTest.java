package com.cvc.cvcms.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ZZJ
 * @date 2021/3/24 12:32
 * @desc
 */
@SpringBootTest
class JwtTokenUtilTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static String token;

    @Test
    void agenerateToken(){
        List<String> permission = new ArrayList<>();
        permission.add("p1");
        permission.add("p2");
        permission.add("p3");
        permission.add("p4");
        permission.add("p5");
        permission.add("p6");
        String[] permissionstrs = new String[permission.size()];
        permission.toArray(permissionstrs);
        UserDetails userDetails = User.builder()
                .username("admin")
                .password("admin")
                .authorities(permissionstrs)
                .disabled(false)
                .build();
        token = jwtTokenUtil.generateToken(userDetails, permission);
        System.out.println(token);
        assert token!=null;
    }

    @Test
    void bgetPermission(){
        List<String> permissions = jwtTokenUtil.getPermissionFromToken(token);
        System.out.println(permissions);
        assert permissions!=null;
    }

    @Test
    void cgetUsername(){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(username);
        assert username!=null;
    }

}