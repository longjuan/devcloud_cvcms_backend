package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 20:41
 * @Description:
 */
@Repository
@Mapper
public interface RoleDao {
    /**
     * 根据权限名获得权限表
     * @param: roleName
     * @return: com.cvc.cvcms.pojo.Role
     */
    Role getRoleByRoleName(String roleName);
}
