package com.cvc.cvcms.service;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author XiuGitHung
 * @date 2021/3/28 17:20
 * @desc goods的service层
 */
@Service
public interface GoodsService {

    /**
     * 增加商品
     * @param: [goods]
     * @return: boolean
     */
    boolean addGood(Goods goods);

    /**
     * 删除商品
     * @param: [goodsId]
     * @return: void
     */
    String deleteGoods(Integer goodsId);

    /**
     * 更新商品
     * @param: [goods]
     * @return: void
     */
    boolean updateGoods(Goods goods);

    /**
     * 根据条件查询商品
     * @param goodsName
     * @param classification
     * @param onSale
     * @param page
     * @param pagesize
     * @return
     */
    List<Goods> getGoodsByConditions(String goodsName,String classification,Integer onSale,Integer page,Integer pagesize);

    /**
     * 根据条件查询商品数量
     * @param goodsName
     * @param classification
     * @param onSale
     * @return
     */
    int getNumOfFindGoodsByConditions(String goodsName,String classification,Integer onSale);

    /**
     * 根据id查询商品
     * @param: [id]
     * @return: com.cvc.cvcms.pojo.Goods
     */
    Goods findGoodsById(Integer id);

    /**
     * 商品上架
     * @param: [id]
     * @return: void
     */
    String GoodsUp(Integer id);

    /**
     * 商品下架
     * @param: [id]
     * @return: void
     */
    String GoodsDown(Integer id);

    /**
     * 商品是否允许小数为数量
     * @param barCode
     * @return
     */
    boolean allowDecimals(String barCode) throws BusinessException;

    /**
     * 根据barcode获取商品
     * @param barcode
     * @return
     */
    Goods getGoodsByBarcode(String barcode);


    List<Goods> getGoodsBySupplierId(Integer supplierId);
}
