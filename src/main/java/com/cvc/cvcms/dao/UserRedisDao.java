package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author ZZJ
 * @date 2021/3/24 18:40
 * @desc
 */
@Repository
public interface UserRedisDao {
    /**
     * 从redis里拿到userdetails
     * @param username
     * @return
     */
    public User loadUserByUsernameFromRedis(String username);

    /**
     * 把UserDetails放进redis
     * @param userDetails
     */
    public void setUserDetailsToRedis(User user);

    /**
     * 把token放进redis
     * @param username
     * @param token
     */
    public void setUserToken(String username,String token);

    /**
     * 获取redis里的最新token
     * @param username
     * @return
     */
    public String getUserToken(String username);


}
