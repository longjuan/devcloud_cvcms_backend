package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.MemberInfo;
import com.cvc.cvcms.pojo.MemberPointsExchange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/7 17:01
 * @desc
 */
@Repository
@Mapper
public interface MemberDao {

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

    /**
     * 添加会员
     * @param username
     * @return
     */
    int addMemberInfo(String username);
}
