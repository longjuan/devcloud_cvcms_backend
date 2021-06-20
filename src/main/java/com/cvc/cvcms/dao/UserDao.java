package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.pojo.dto.UserStaffDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/24 14:16
 * @desc user的dao层
 */
@Repository
@Mapper
public interface UserDao {
    /**
     * 根据性别或身份查询用户
     * @param: [role, gender]
     * @return: java.util.List<com.cvc.cvcms.pojo.User>
     */
    List<UserStaffDTO> getUserByCondition(Integer roleId,
                                          Integer gender,
                                          String name);

    /**
     * 根据用户名找出用户
     * @param username
     * @return
     */
     User getUserByName(String username);

    /**
     * 根据角色名查询权限
     * @param roleName
     * @return
     */
     String[] getPermissionByRoleName(String roleName);

    /**
     * 根据id冻结账户
     * @param: [userId, flag]
     * @return: void
     */
     int freezeByUserId(Integer userId);

    /**
     * 根据id解冻账户
     * @param: [userId]
     * @return: void
     */
     int thawByUserId(Integer userId);

    /**
     * 内部注册
     * @param: [username, password, roleId]
     * @return: void
     */
     int add( @Param("username")String username,
                    @Param("password")String password,
                    @Param("roleid")Integer roleId);

     int addInfo( @Param("id") Integer id,
                        @Param("name")String username,
                        @Param("gender") Integer gender,
                        @Param("phone")String phone,
                        @Param("time") Date entryTime,
                        @Param("picture")String picture);

    /**
     * 获取所有用户 不带密码
     * @return
     */
     List<User> getUsersNoPassword();

    /**
     * 根据id找出用户
     * @param id
     * @return
     */
     User getUserById(Integer id);

    /**
     * 获取所有员工 不带密码
     * @param: []
     * @return: com.cvc.cvcms.pojo.User
     */
     List<UserStaffDTO> getStaffsNoPassword();

    /**
     * 根据名称找出员工
     * @param username
     * @return
     */
     UserStaffDTO getStaffByName(String username);

    /**
     * 修改密码
     * @param id
     * @param password
     */
     int updatePW(Integer id,String password);

    /**
     * 获取用户数量
     * @param:
     * @return: int
     */
     int getAll();

    /**
     * 商品上架数量
     * @param: []
     * @return: int
     */
     int getGoodsOnSale();

    /**
     * 获取投诉数量
     * @param: []
     * @return: int
     */
     int getComplain();

    /**
     * 当天营业额
     * @param: []
     * @return: int
     */
     BigDecimal getMoney();

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
