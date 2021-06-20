package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.RoleDao;
import com.cvc.cvcms.pojo.Role;
import com.cvc.cvcms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 20:46
 * @Description:
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleDao.getRoleByRoleName(roleName);
    }
}
