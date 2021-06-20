package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.StaffInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZZJ
 * @date 2021/4/1 18:15
 * @desc
 */
@Repository
@Mapper
public interface StaffInfoDao {
    /**
     * 通过userid得到员工信息
     * @param userId
     * @return
     */
    StaffInfo getByUserid(Integer userId);

    /**
     * 获取所有员工信息
     * @param: []
     * @return: com.cvc.cvcms.pojo.StaffInfo
     */
    List<StaffInfo> getAll();

    /**
     * 获取员工总数
     * @param: []
     * @return: int
     */
    int getNums();
}
