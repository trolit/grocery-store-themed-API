package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;

public interface CategoryCommandService {

    int createCategory(CategoryCreateDto categoryCreateDto);

    CategoryQueryDto updateCategory(int id, CategoryUpdateDto categoryUpdateDto);

    int deleteCategory(int id);

}
