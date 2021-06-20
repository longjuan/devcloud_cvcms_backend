package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author XiuGitHung
 * @date 2021/3/28 17:16
 * @desc goods的dao层
 */
@Repository
@Mapper
public interface GoodsDao {

    /**
     * 增加商品
     * @param: [goods]
     * @return: int
     */
    int addGood(Goods goods);

    /**
     * 删除商品
     * @param: [goodsId]
     * @return: void
     */
    void deleteGoods(Integer goodsId);

    /**
     * 更新商品
     * @param: [goods]
     * @return: void
     */
    int updateGoods(Goods goods);

    /**
     * 根据id获得唯一商品
     * @param: [id]
     * @return: com.cvc.cvcms.pojo.Goods
     */
    Goods findGoodsById(Integer id);

    /**
     * 商品上架
     * @param: [id]
     * @return: void
     */
    void GoodsUp(Integer id);

    /**
     * 商品下架
     * @param: [id]
     * @return: void
     */
    void GoodsDown(Integer id);

    /**
     * 根据barcode 获得商品信息
     * @param barCode
     * @return
     */
    Goods getGoodsByBarCode(String barCode);

    /**
     * 根据条件获得goods
     * @param goodsName
     * @param classification
     * @param onSale
     * @param offset
     * @param size
     * @return
     */
    List<Goods> getGoodsByConditions(String goodsName,String classification,Integer onSale,Integer offset,Integer size);

    /**
     * 根据条件获得goods的数量
     * @param goodsName
     * @param classification
     * @param onSale
     * @return
     */
    int getNumOfGoodsByConditions(String goodsName,String classification,Integer onSale);

    List<Goods> getGoodsBySupplierId(Integer supplierId);
}
