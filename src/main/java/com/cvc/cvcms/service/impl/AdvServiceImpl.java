package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.AdvDao;
import com.cvc.cvcms.pojo.Advertisement;
import com.cvc.cvcms.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/1 22:30
 * @Description:
 */
@Service
@Transactional
public class AdvServiceImpl implements AdvService {

    @Autowired
    AdvDao advDao;
    @Autowired
    Advertisement advertisement;

    @Override
    public boolean release(String img, String detail, Date startTime, Date endTime) {
        return advDao.release(img,detail,startTime,endTime) > 0 ;
    }

    @Override
    public boolean deleteAdvertisement(Integer advertisementId) {
        return advDao.deleteAdvertisementById(advertisementId) > 0 ;
    }

    @Override
    public Advertisement findAdvertisementById(Integer advertisementId) {
        return advDao.findAdvertisementById(advertisementId);
    }

    @Override
    public List<Advertisement> findALLAdvertisement() {
        return advDao.findAllAdvertisement();
    }

    @Override
    public List<Advertisement> findALLPublishedAdvertisement() {
        return advDao.findAllPublishedAdvertisement();
    }

    @Override
    public boolean updateAdvertisement(Advertisement advertisement) {
        return advDao.updateAdvertisement(advertisement) > 0;
    }
}
