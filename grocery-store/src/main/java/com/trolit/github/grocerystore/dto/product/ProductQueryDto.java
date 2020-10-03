package com.trolit.github.grocerystore.dto.product;

import java.math.BigDecimal;

public class ProductQueryDto {

    private Integer id;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private String category;

    private Integer categoryId;

    private String measurement;

    private BigDecimal previousPrice;

    private Integer percentagePriceDiff;

    private String priceStatus;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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
