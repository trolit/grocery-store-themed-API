package com.trolit.github.grocerystore.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ProductCreateDto {
    @NotEmpty(message = "Product name cannot be empty.")
    @Min(value = 3, message = "Product name must have at least 3 letters.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
