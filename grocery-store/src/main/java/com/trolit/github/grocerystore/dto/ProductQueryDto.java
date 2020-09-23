package com.trolit.github.grocerystore.dto;

public class ProductQueryDto {
    private Integer id;

    private String name;

    public ProductQueryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
