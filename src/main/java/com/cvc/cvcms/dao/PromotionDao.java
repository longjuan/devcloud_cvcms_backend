package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Promotion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/4 11:12
 * @Description:
 */
@Mapper
@Repository
public interface PromotionDao {
    /**
     * 添加折扣
     * @param ： [goodsId, promotionPrice, startTime, endTime]
     * @return
     */
    int addPromotion(Promotion promotion);

    /**
     * 通过ID删除折扣
     * @param goodsId
     * @return
     */
    int deletePromotionById(Integer goodsId);

    /**
     * 根据商品id修改折扣价格、折扣时间
     * @param: [goodsId, promotionPrice, startTime, endTime]
     * @return: int
     */
    int updatePromotion(Promotion promotion);

    /**
     * 通过ID查询折扣
     * @param goodsId
     * @return
     */
    Promotion findPromotionById(Integer goodsId);

    /**
     * 查询所有折扣
     * @return
     */
    List<Promotion> findAllPromotion();
}
