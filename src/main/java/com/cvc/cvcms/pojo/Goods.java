package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods {

    private Integer id;
    private String goodsName;
    private BigDecimal price;
    private BigDecimal stock;
    private BigDecimal stockWarning;
    private Integer stockDecimal;
    private String barCode;
    private Integer onSale;
    private Integer supplierId;
    private String stockUnit;
    private String classification;
    private BigDecimal promotionPrice;
    private Date startTime;
    private Date endTime;

    public Goods(Integer id, String goodsName, BigDecimal price, BigDecimal stock, BigDecimal stockWarning, Integer stockDecimal, String barCode, Integer onSale, Integer supplierId, String stockUnit, String classification) {
        this.id = id;
        this.goodsName = goodsName;
        this.price = price;
        this.stock = stock;
        this.stockWarning = stockWarning;
        this.stockDecimal = stockDecimal;
        this.barCode = barCode;
        this.onSale = onSale;
        this.supplierId = supplierId;
        this.stockUnit = stockUnit;
        this.classification = classification;
    }
}
