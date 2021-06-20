package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.common.JwtTokenUtil;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.dao.UserRedisDao;
import com.cvc.cvcms.pojo.MemberInfo;
import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.pojo.StaffInfo;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.service.*;
import com.cvc.cvcms.service.impl.MemberServiceImpl;
import com.cvc.cvcms.service.impl.MyDataUserDetailsServiceImpl;
import com.cvc.cvcms.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author ZZJ
 * @date 2021/3/14 20:05
 * @desc jwt登陆点
 */
@RestController
public class JwtAuthController {
    @Autowired
    private MyDataUserDetailsServiceImpl myDataUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRedisDao userRedisDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtAuthService jwtAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private MemberServiceImpl memberService;




    @PostMapping("/user/login")
    public JsonStandard login(String username,String password){
        if(!(StringUtils.hasText(username) && StringUtils.hasText(password))){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"用户名和密码不能为空");
        }
        try {
            String token = jwtAuthService.login(username, password);
            HashMap<String, Object> ret = new HashMap<>(5);
            ret.put("token",token);
            ret.put("username",username);
            User user = userService.getUserByUsername(username);
            Role role = roleService.getRoleByRoleName(user.getRoleName());
            StaffInfo staffInfo = userInfoService.getStaffInfoById(user.getId());
            ret.put("info",staffInfo);
            ret.put("role",role);
            return JsonStandard.success("登陆成功", ret);
        } catch (RuntimeException e) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,e.getMessage());
        }
    }

    /**
     * 更换token
     * @param oleToken
     * @return
     */
    @PostMapping("/refresh")
    public JsonStandard refresh(@RequestHeader("${jwt.header}")String oleToken){
        String token = jwtAuthService.refresh(oleToken);
        HashMap<String, Object> ret = new HashMap<>(1);
        ret.put("token",token);
        return JsonStandard.success(ret);
    }

    @PostMapping("/wechat/login")
    public JsonStandard wechatLogin(String code){
        //TODO 微信登录未开发完成
        try {
            String username = jwtAuthService.wechatLoginPrepare(code);
            User user = userService.getUserByUsername(username);
            if(user == null){
                if(memberService.register(username, "123")){
                    JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE, "注册失败");
                }
            }

            UserDetails userDetails = myDataUserDetailsService.loadUserByUsername(username);
            userRedisDao.setUserDetailsToRedis(new User(userDetails));
            String token = jwtTokenUtil.generateToken(userDetails, Arrays.asList(userDao.getPermissionByRoleName(userDao.getUserByName(username).getRoleName())));
            userRedisDao.setUserToken(username,token);
            MemberInfo memberInfo = wechatService.getMemberInfo(username);



            HashMap<String, Object> map = new HashMap<>(2);
            map.put("token",token);
            map.put("memberInfo",memberInfo);
            return JsonStandard.success(map);
        } catch (BusinessException e) {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,e.getMessage());
        }
    }
}
