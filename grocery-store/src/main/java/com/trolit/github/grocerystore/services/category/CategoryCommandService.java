package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;

public interface CategoryCommandService {
    public int createCategory(CategoryCreateDto categoryCreateDto);

    public CategoryQueryDto updateCategory(int id, CategoryUpdateDto categoryUpdateDto);

    public int deleteCategory(int id);
}
