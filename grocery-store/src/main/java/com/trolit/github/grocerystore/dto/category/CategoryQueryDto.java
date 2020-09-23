package com.trolit.github.grocerystore.dto.category;

public class CategoryQueryDto {
    private Integer id;

    private String name;

    public CategoryQueryDto(Integer id, String name) {
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
