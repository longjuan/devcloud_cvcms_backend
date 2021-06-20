package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Advertisement;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 22:26
 * @Description:
 */
@Service
public interface AdvService {
    /**
     * 发布广告
     * @param: [img, detail, startTime, endTime]
     * @return: int
     */
    boolean release(String img, String detail, Date startTime, Date endTime);

    /**
     * 删除广告
     * @param advertisementId
     * @return
     */
    boolean deleteAdvertisement(Integer advertisementId);

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
    List<Advertisement> findALLAdvertisement();

    /**
     * 查询所有上架广告
     * @return
     */
    List<Advertisement> findALLPublishedAdvertisement();

    /**
     * 更改广告信息
     * @param advertisement
     * @return
     */
    boolean updateAdvertisement(Advertisement advertisement);



}
