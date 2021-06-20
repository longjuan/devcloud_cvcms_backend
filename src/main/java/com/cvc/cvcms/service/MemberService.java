package com.cvc.cvcms.service;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author nwer
 */
@Service
public interface MemberService {
    /**
     * 注册会员
     * @param username
     * @param password
     * @return
     */
    public boolean register(String username, String password);
}
