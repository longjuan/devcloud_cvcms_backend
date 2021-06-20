package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Complain;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/9 9:09
 * @Description:
 */
@Service
public interface ComplainService {
    /**
     * 新增投诉
     * @param: [complain]
     * @return: int
     */
    boolean addComplain(Complain complain);

    /**
     * 根据投诉id号修改投诉状态
     * @param: [complain]
     * @return: int
     */
    boolean updateComplain(Complain complain);

    /**
     * 查询所有投诉
     * @param: []
     * @return: List<Complain>
     */
    List<Complain> getAll();

    /**
     * 根据用户id获取投诉
     * @param: []
     * @return: com.cvc.cvcms.pojo.Complain
     */
    Complain getComplainById(Integer userId);
}
