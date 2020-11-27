package com.trolit.github.grocerystore.dto.product;

import java.math.BigDecimal;

public class UpdatedProductPriceDto {

    private Integer id;

    private BigDecimal price;

    private BigDecimal previousPrice;

    private Integer percentagePriceDiff;

    private String priceStatus;

    public UpdatedProductPriceDto() { }

    public UpdatedProductPriceDto(Integer id, BigDecimal price,
                                  BigDecimal previousPrice, Integer percentagePriceDiff,
                                  String priceStatus) {
        this.id = id;
        this.price = price;
        this.previousPrice = previousPrice;
        this.percentagePriceDiff = percentagePriceDiff;
        this.priceStatus = priceStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }

    public Integer getPercentagePriceDiff() {
        return percentagePriceDiff;
    }

    public void setPercentagePriceDiff(Integer percentagePriceDiff) {
        this.percentagePriceDiff = percentagePriceDiff;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus;
    }
}
