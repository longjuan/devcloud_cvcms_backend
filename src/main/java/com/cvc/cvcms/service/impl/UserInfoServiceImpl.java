package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.StaffInfoDao;
import com.cvc.cvcms.pojo.StaffInfo;
import com.cvc.cvcms.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/1 18:21
 * @desc
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private StaffInfoDao staffInfoDao;

    @Override
    public StaffInfo getStaffInfoById(Integer userId) {
        return staffInfoDao.getByUserid(userId);
    }

    @Override
    public List<StaffInfo> getAll() {
        return staffInfoDao.getAll();
    }
}
