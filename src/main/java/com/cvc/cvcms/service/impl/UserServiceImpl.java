package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.StaffInfoDao;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.pojo.dto.UserStaffDTO;
import com.cvc.cvcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author XiuGitHung
 * @date 2021/3/26 12:05
 * @desc user的service层
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Autowired
    StaffInfoDao staff;

    @Override
    public List<UserStaffDTO> getUserByCondition(Integer roleId, Integer gender,String name) {
        return userDao.getUserByCondition(roleId,gender,name);
    }

    @Override
    public boolean freezeByUserId(Integer userId) {
        return userDao.freezeByUserId(userId) > 0;
    }

    @Override
    public boolean thawByUserId(Integer userId) {
        return userDao.thawByUserId(userId) > 0;
    }

    @Override
    public int add(String username, String password, Integer roleId) {
        return userDao.add(username,password,roleId);
    }

    @Override
    public int addInfo(Integer id,String name ,Integer gender, String phone, Date entryTime, String picture) {
        return userDao.addInfo(id,name,gender,phone,entryTime,picture);
    }

    @Override
    public boolean regist(String username, String password, Integer roleId, String name, Integer gender, String phone, Date entryTime, String picture) {
        return add(username,password,roleId) > 0 && addInfo(userDao.getUserByName(username).getId(),name,gender,phone,entryTime,picture) > 0;
    }

    @Override
    public List<User> getUsersNoPassword() {
        return userDao.getUsersNoPassword();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userDao.getUserByName(username);
        if (user != null){
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public List<UserStaffDTO> getStaffsNoPassword() {
        return userDao.getStaffsNoPassword();
    }

    @Override
    public UserStaffDTO getStaffByName(String username) {
        return userDao.getStaffByName(username);
    }

    @Override
    public String updatePW(Integer userid, String oldpassword, String newpassword) {
        User user = userDao.getUserById(userid);
        if (user == null){
            return "无此用户";
        }
        // 验证密码
        boolean checkpw = BCrypt.checkpw(oldpassword, user.getPassword());
        if (!checkpw){
            return "旧密码不正确";
        }
        userDao.updatePW(userid,BCrypt.hashpw(newpassword,BCrypt.gensalt()));
        return null;
    }

    @Override
    public int getAll() {
        return staff.getNums();
    }

    @Override
    public int getGoodsOnSale() {
        return userDao.getGoodsOnSale();
    }

    @Override
    public BigDecimal getMoney() {
        return userDao.getMoney();
    }

    @Override
    public BigDecimal getYesterdayMoney() {
        return userDao.getYesterdayMoney();
    }

    @Override
    public List<Role> getRole() {
        return userDao.getRole();
    }

    @Override
    public User getMemberByName(String username) {
        return userDao.getMemberByName(username);
    }

    @Override
    public int getComplain() {
        return userDao.getComplain();
    }

}
