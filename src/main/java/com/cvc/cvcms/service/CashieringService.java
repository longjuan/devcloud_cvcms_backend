package com.cvc.cvcms.service;

import com.cvc.cvcms.common.BusinessException;
import com.cvc.cvcms.pojo.Coupon;
import com.cvc.cvcms.pojo.Goods;
import com.cvc.cvcms.pojo.dto.SalesItemDTO;
import com.cvc.cvcms.pojo.dto.SettlementDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/29 12:54
 * @desc
 */
@Service
public interface CashieringService {

    /**
     * 根据条形码获取当时价格
     * @param barCode
     * @return
     */
    public BigDecimal getPrice(String barCode);

    /**
     * 根据商品和时间获取当前价格
     * @param goods
     * @param now
     * @return
     */
    public BigDecimal getCurrentPrice(Goods goods, Date now);

    /**
     * 结算
     * @param settlementDTO
     * @return
     */
    public BigDecimal settlement(SettlementDTO settlementDTO) throws BusinessException;

    /**
     * 获得优惠的金额
     * @param settlementDTO
     * @return
     * @throws BusinessException 如果不满足就抛异常
     */
    public BigDecimal getPreferentialAmount(SettlementDTO settlementDTO) throws BusinessException;

}
