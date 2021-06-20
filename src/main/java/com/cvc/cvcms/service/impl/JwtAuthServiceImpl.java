package com.cvc.cvcms.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.common.HttpClientUtil;
import com.cvc.cvcms.common.JwtTokenUtil;
import com.cvc.cvcms.dao.MemberDao;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.dao.UserRedisDao;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.service.JwtAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/14 20:17
 * @desc jwt封装token处理逻辑service
 */
@Service
@Slf4j
class JwtAuthServiceImpl implements JwtAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyDataUserDetailsServiceImpl myDataUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRedisDao userRedisDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private UserServiceImpl userService;

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.grant_type}")
    private String grant_type;
    @Value("${wechat.login_url}")
    private String login_url;

    /**
     * 登录认证换取jwt令牌
     * @param username
     * @param password
     * @return jwt令牌
     */
    @Override
    public String login(String username, String password) throws RuntimeException{
        try {
            // 封装
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(upToken);
            // 认证
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new RuntimeException("用户名或密码不正确");
        }
        // 构造userDetails
        UserDetails userDetails = myDataUserDetailsService.loadUserByUsername(username);
        // 放进redis
        userRedisDao.setUserDetailsToRedis(new User(userDetails));
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails, Arrays.asList(userDao.getPermissionByRoleName(userDao.getUserByName(username).getRoleName())));
        // 放token进redis
        userRedisDao.setUserToken(username,token);
        return token;
    }

    /**
     * 更换过期令牌
     * @param oldToken
     * @return
     */
    @Override
    public String refresh(String oldToken){
        if (jwtTokenUtil.isTokenExpired(oldToken)){
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }

    @Override
    public User loadUserByUsernameFromRedis(String username) {
        User user = userRedisDao.loadUserByUsernameFromRedis(username);
        return user;
    }

    @Override
    public String wechatLoginPrepare(String code) throws BusinessException {
        //TODO 微信登录未开发完成
        // 去微信拿登录信息
        //String openid = this.getOpenidFromWeChat(code);
        String username = "wechat_" + code.substring(0,5);
//        User user = userDao.getUserByName(username);
//
//        if (user == null){
//            String password = BCrypt.hashpw(username,BCrypt.gensalt());
//            memberService.register(username, password);
//        }
        return username;
    }

    private String getOpenidFromWeChat(String code) throws BusinessException {
        // 配置请求参数
        Map<String, String> param = new HashMap<>(4);
        param.put("appid", appid);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", grant_type);
        // 发送请求
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String wxResult = httpClientUtil.doGet(login_url,param);
        try {
            JSONObject jsonObject = JSONObject.parseObject(wxResult);
            // 拿到内容
            if (jsonObject.getInteger("errcode") != 0){
                throw new BusinessException("登录失败：" + jsonObject.getString("errmsg"));
            }
            return jsonObject.getString("openid");
        } catch (JSONException e) {
            log.error("json转换出错",e);
        } catch (NullPointerException e){
            log.error("微信登录json转换出错",e);
        }
        return null;
    }


}
