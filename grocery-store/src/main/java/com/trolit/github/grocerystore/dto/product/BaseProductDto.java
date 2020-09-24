package com.trolit.github.grocerystore.dto.product;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class BaseProductDto {

    @NotEmpty(message = "Product name cannot be empty.")
    @Size(min = 3, message = "Product name must have at least 3 letters.")
    protected String name;

    @NotNull
    @Digits(integer=4, fraction=2, message = "Maximum allowed possible value for price is: 9999.99")
    protected BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Stock size must be set to minimum 0.")
    protected Integer stock;

    @NotNull
    protected Integer categoryId;

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
