package com.cvc.cvcms.dao.impl;

import com.cvc.cvcms.common.JwtTokenUtil;
import com.cvc.cvcms.dao.UserRedisDao;
import com.cvc.cvcms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author ZZJ
 * @date 2021/3/24 18:41
 * @desc
 */
@Repository
public class UserRedisDaoImpl implements UserRedisDao {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User loadUserByUsernameFromRedis(String username) {
        User user = (User) redisTemplate.opsForValue().get("UserDetails:"+username);
        return user;
    }

    @Override
    public void setUserDetailsToRedis(User user) {
        redisTemplate.opsForValue().set("UserDetails:"+user.getUsername(),user, jwtTokenUtil.getExpiration(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void setUserToken(String username, String token) {
        redisTemplate.opsForValue().set("token:"+username,token,jwtTokenUtil.getExpiration(),TimeUnit.MILLISECONDS);
    }

    @Override
    public String getUserToken(String username) {
        return (String) redisTemplate.opsForValue().get("token:"+username);
    }

}
