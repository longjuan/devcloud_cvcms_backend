package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.MemberInfo;
import com.cvc.cvcms.pojo.MemberPointsExchange;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/7 16:45
 * @desc
 */
@Service
public interface WechatService {

    /**
     * 根据username获取会员信息
     * @param username
     * @return
     */
    MemberInfo getMemberInfo(String username);

    /**
     * 根据username获取兑换记录
     * @param username
     * @return
     */
    List<MemberPointsExchange> getMemberPointsExchanges(String username);
}
