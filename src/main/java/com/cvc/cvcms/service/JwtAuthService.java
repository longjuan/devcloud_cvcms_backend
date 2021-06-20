package com.cvc.cvcms.service;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author ZZJ
 * @date 2021/3/24 14:04
 * @desc jwt封装token处理逻辑service
 */
@Service
public interface JwtAuthService {
    /**
     * 登录换取jwt令牌
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);

    /**
     * 更换过期令牌
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);

    /**
     * 从redis里拿到userdetails
     * @param username
     * @return
     */
    User loadUserByUsernameFromRedis(String username);

    /**
     * 微信登录换取token
     * @param code
     * @return username
     */
    String wechatLoginPrepare(String code) throws BusinessException;
}
