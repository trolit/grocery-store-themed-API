package com.trolit.github.grocerystore.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductStockOnlyDto {

    @NotNull
    @Min(value = 0, message = "Stock size must be set to minimum 0.")
    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
