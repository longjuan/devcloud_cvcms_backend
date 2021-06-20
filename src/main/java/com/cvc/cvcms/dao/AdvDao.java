package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 22:22
 * @Description:
 */
@Mapper
@Repository
public interface AdvDao {
    /**
     * 发布广告
     * @param: [img, detail, startTime, endTime]
     * @return: int
     */
    int release(String img, String detail, Date startTime, Date endTime);

    /**
     * 删除广告
     * @param advertisementId
     * @return
     */
    int deleteAdvertisementById(Integer advertisementId);

    /**
     * 通过ID查询广告
     * @param advertisementId
     * @return
     */
    Advertisement findAdvertisementById(Integer advertisementId);

    /**
     * 查询所有广告
     * @return
     */
    List<Advertisement> findAllAdvertisement();

    /**
     * 查询所有上架广告
     * @return
     */
    List<Advertisement> findAllPublishedAdvertisement();

    /**
     * 更改广告信息
     * @param advertisement
     * @return
     */
    int updateAdvertisement(Advertisement advertisement);






}
