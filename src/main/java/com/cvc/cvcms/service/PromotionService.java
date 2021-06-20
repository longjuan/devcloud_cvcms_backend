package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Promotion;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 11:26
 * @Description:
 */
@Service
public interface PromotionService {


    /**
     * 根据商品id修改折扣价格、折扣时间
     * @param: [goodsId, promotionPrice, startTime, endTime]
     * @return: int
     */
    boolean updatePromotion(Promotion promotion);

    /**
     * 添加折扣
     * @param ： [goodsId, promotionPrice, startTime, endTime]
     * @return
     */
    boolean addPromotion(Promotion promotion);

    /**
     * 通过ID删除折扣
     * @param promotionId
     * @return
     */
    boolean deletePromotionById(Integer promotionId);

    /**
     * 通过ID查询折扣
     * @param promotionId
     * @return
     */
    Promotion findPromotionById(Integer promotionId);

    /**
     * 查询所有折扣
     * @return
     */
    List<Promotion> findAllPromotion();
}
