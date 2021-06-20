package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.StaffInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/1 18:18
 * @desc 员工信息和顾客信息的service
 */
@Service
public interface UserInfoService {
    /**
     * 通过userid获取员工信息
     * @param userId
     * @return
     */
    StaffInfo getStaffInfoById(Integer userId);

    /**
     * 获取所有员工信息
     * @param: []
     * @return: com.cvc.cvcms.pojo.StaffInfo
     */
    List<StaffInfo> getAll();
}
