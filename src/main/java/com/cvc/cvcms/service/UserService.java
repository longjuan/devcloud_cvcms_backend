package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.pojo.dto.UserStaffDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Service
public interface UserService {

    /**
     * 根据性别或身份查询用户
     * @param: [role, gender]
     * @return: java.util.List<com.cvc.cvcms.pojo.User>
     */
    List<UserStaffDTO> getUserByCondition(Integer roleId,
                                          Integer gender,
                                          String name);

    /**
     * 根据id冻结账户
     * @param: [userId, flag]
     * @return: void
     */
    public boolean freezeByUserId(Integer userId);

    /**
     * 根据id解冻账户
     * @param: [userId]
     * @return: void
     */
    public boolean thawByUserId(Integer userId);

    /**
     * 新增用户
     * @param: [username, password, roleId]
     * @return: void
     */
    public int add(String username,String password,Integer roleId);

    /**
     * 新增员工
     * @param: [id, gender, phone, entryTime, picture]
     * @return: int
     */
    public int addInfo(Integer id, String name , Integer gender, String phone, Date entryTime, String picture);

    /**
     * 注册 合并了add和addInfo方法
     * @param: []
     * @return: boolean
     */
    public boolean regist(String username,String password,Integer roleId, String name , Integer gender, String phone, Date entryTime, String picture);

    /**
     * 获取用户列表 不查密码
     * @return
     */
    public List<User> getUsersNoPassword();

    /**
     * 通过用户名找用户 不查密码
     * @return
     */
    public User getUserByUsername(String username);

    /**
     * 获取所有员工 不带密码
     * @param: []
     * @return: com.cvc.cvcms.pojo.User
     */
    public List<UserStaffDTO> getStaffsNoPassword();

    /**
     * 根据名称找出员工
     * @param username
     * @return
     */
    public UserStaffDTO getStaffByName(String username);

    /**
     * 修改密码
     * @param userid
     * @param oldpassword
     * @param newpassword
     * @return
     */
    public String updatePW(Integer userid,String oldpassword,String newpassword);

    /**
     * 获取用户数量
     * @param:
     * @return: int
     */
    public int getAll();

    /**
     * 商品上架数量
     * @param: []
     * @return: int
     */
    public int getGoodsOnSale();

    /**
     * 获取投诉数量
     * @param: []
     * @return: int
     */
    public int getComplain();


    /**
     * 当天营业额
     * @param: []
     * @return: int
     */
    public BigDecimal getMoney();

    /**
     * 获取昨天营业额
     * @param: []
     * @return: java.math.BigDecimal
     */
    BigDecimal getYesterdayMoney();

    /**
     * 获取所有职位
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    List<Role> getRole();

    /**
     * 判断会员是否存在
     * @param: [username]
     * @return: com.cvc.cvcms.pojo.User
     */
    User getMemberByName(String username);
}
