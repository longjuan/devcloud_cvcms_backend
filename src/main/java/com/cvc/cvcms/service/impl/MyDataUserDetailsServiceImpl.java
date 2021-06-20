package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ZZJ
 * @date 2021/3/12 16:12
 * @desc 与用户和权限数据库交互service
 */
@Service
public class MyDataUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名拿到自己的user类
        com.cvc.cvcms.pojo.User user = userDao.getUserByName(username);
        // 查不到就返回空
        if (user == null) {
            return null;
        }
        // 根据用户拿到权限
//        String[] permissions = userDao.getPermissionByRoleName(user.getRoleName());
        // 转成UserDetails 让框架识别
        UserDetails userDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoleName())
                .disabled(user.getFrozen())
                .build();
        return userDetails;
    }


}
