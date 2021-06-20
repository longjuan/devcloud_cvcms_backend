package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.MemberDao;
import com.cvc.cvcms.pojo.MemberInfo;
import com.cvc.cvcms.pojo.MemberPointsExchange;
import com.cvc.cvcms.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/7 17:00
 * @desc
 */
@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    MemberDao memberDao;
    @Override
    public MemberInfo getMemberInfo(String username) {
        return memberDao.getMemberInfo(username);
    }

    @Override
    public List<MemberPointsExchange> getMemberPointsExchanges(String username) {
        return memberDao.getMemberPointsExchanges(username);
    }
}
