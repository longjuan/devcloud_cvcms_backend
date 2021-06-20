package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.PromotionDao;
import com.cvc.cvcms.pojo.Promotion;
import com.cvc.cvcms.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 11:27
 * @Description:
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionDao promotionDao;

    @Override
    public boolean updatePromotion(Promotion promotion) {
        return promotionDao.updatePromotion(promotion) > 0 ;
    }

    @Override
    public boolean addPromotion(Promotion promotion) {
        return promotionDao.addPromotion(promotion) > 0;
    }

    @Override
    public boolean deletePromotionById(Integer promotionId) {
        return promotionDao.deletePromotionById(promotionId) > 0;
    }

    @Override
    public Promotion findPromotionById(Integer goodsId) {
        return promotionDao.findPromotionById(goodsId);
    }

    @Override
    public List<Promotion> findAllPromotion() {
        return promotionDao.findAllPromotion();
    }
}
