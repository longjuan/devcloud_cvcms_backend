package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 20:46
 * @Description:
 */
@Service
public interface RoleService {
    /**
     * 根据权限名获得权限表
     * @param: roleName
     * @return: com.cvc.cvcms.pojo.Role
     */
    Role getRoleByRoleName(String roleName);
}
