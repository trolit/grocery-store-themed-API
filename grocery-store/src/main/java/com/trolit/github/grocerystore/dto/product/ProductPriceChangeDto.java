package com.trolit.github.grocerystore.dto.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductPriceChangeDto {

    @NotNull
    @Min(value = 1, message = "Percentage field must be set to at least 1.")
    @Max(value = 300, message = "Maximum value for percentage is 300.")
    private Integer percentage;

    // getters and setters

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

}
