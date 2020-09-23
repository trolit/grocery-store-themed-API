package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;

import java.util.List;

public interface CategoryQueryService {
    public CategoryQueryDto getCategory(int id);

    public List<CategoryQueryDto> getAllCategories();
}
