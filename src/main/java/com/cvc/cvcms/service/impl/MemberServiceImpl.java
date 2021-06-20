package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.MemberDao;
import com.cvc.cvcms.dao.UserDao;
import com.cvc.cvcms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nwer
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    UserDao useDao;
    @Autowired
    MemberDao memberDao;

    @Override
    public boolean register(String username, String password) {
        return useDao.add(username, password, 5) > 0 && memberDao.addMemberInfo(username ) > 0  ;
    }
}
