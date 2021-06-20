package com.cvc.cvcms.controller;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.FtpUtil;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.pojo.StaffInfo;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.pojo.dto.UserStaffDTO;
import com.cvc.cvcms.service.RoleService;
import com.cvc.cvcms.service.UserInfoService;
import com.cvc.cvcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@MultipartConfig
public class UserManageController {
    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;

    @Autowired
    FtpUtil ftpUtil;

    @PostMapping("/user/freeze")
//    @PreAuthorize("hasAuthority('')")
    public JsonStandard freeze(Integer userId, Integer freezed){
        if (freezed == 0){
            boolean freezeByUserId = userService.freezeByUserId(userId);
            if (freezeByUserId){
                return JsonStandard.success("冻结成功");
            }
        }else {
            boolean thawByUserId = userService.thawByUserId(userId);
            if (thawByUserId){
                return JsonStandard.success("解冻成功");
            }
        }
        return JsonStandard.error(ErrorEnum.INNTERNAL_SERVER_ERROR,"帐号异常");
    }


    @PostMapping("/user/getusers")
    @PreAuthorize("hasAnyAuthority('admin')")
    public JsonStandard getUsers(@RequestParam(required = false) String username){
        Map<String, Object> result = new HashMap<>(1);
        // 判断有没有传用户名
        if (username == null){
            List<UserStaffDTO> list = userService.getStaffsNoPassword();
            result.put("user",list);
        }else {
            UserStaffDTO userByUsername = userService.getStaffByName(username);
            if (userByUsername == null){
                return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"no this user");
            }
            result.put("user",userByUsername);
        }
        return JsonStandard.success(result);
    }

    @PostMapping("/user/updatepw")
//    @PreAuthorize("hasAuthority('')")
    public JsonStandard updatePW(Integer userid,String oldpassword,String newpassword){
        //TODO 可以在这加上自己只能修改自己的鉴权

        String updatePW = userService.updatePW(userid, oldpassword, newpassword);
        if (updatePW == null){
            return JsonStandard.success("修改成功");
        }else {
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,updatePW);
        }
    }

    @PostMapping("/user/regist")
//    @PreAuthorize("hasAuthority('')")
    public JsonStandard regist(HttpServletRequest req, String username, String password, Integer roleId, String name, Integer gender, String phone, @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date entryTime, MultipartFile upload) throws IOException {
        if(!(StringUtils.hasText(username) && StringUtils.hasText(password))){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"用户名和密码不能为空");
        }
        if(upload.isEmpty()){
            return JsonStandard.error(ErrorEnum.NOT_ACCEPTABLE,"请先选择上传的图片");
        }

        String filename = UUID.randomUUID().toString().replace("-","_")
                + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf('.'));
        FileInputStream in = (FileInputStream) upload.getInputStream();
        if(ftpUtil.uploadToFtp(filename,in)){
            password = BCrypt.hashpw(password,BCrypt.gensalt());
            if(userService.regist(username,password,roleId,name,gender,phone,entryTime,filename)){
                return JsonStandard.success("注册成功");
            }
        }
        return JsonStandard.error(ErrorEnum.INNTERNAL_SERVER_ERROR,"注册异常");
    }

    @PostMapping("/user/refresh")
    public JsonStandard userRefresh(){
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        HashMap<String, Object> ret = new HashMap<>(5);
        ret.put("username",currentUserName);
        User user = userService.getUserByUsername(currentUserName);
        StaffInfo staffInfo = userInfoService.getStaffInfoById(user.getId());
        Role role = roleService.getRoleByRoleName(user.getRoleName());
        ret.put("info",staffInfo);
        ret.put("role",role);
        return JsonStandard.success(ret);
    }

    @PostMapping("/user/getRole")
    public JsonStandard getRole(){
        Map<String,Object> map = new HashMap<>(1);
        List<Role> list = userService.getRole();
        map.put("role",list);
        return JsonStandard.success("success",map);
    }

    @PostMapping("/user/getByCondition")
//    @PreAuthorize("hasAnyAuthority('admin')")
    public JsonStandard getByCondition(Integer roleId,Integer gender,String name){
        HashMap<String, Object> map = new HashMap<>(1);
        List<UserStaffDTO> list = userService.getUserByCondition(roleId,gender,name);
        map.put("users",list);
        return JsonStandard.success("success",map);
    }
}
