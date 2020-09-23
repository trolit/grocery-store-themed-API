package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;
import com.trolit.github.grocerystore.models.Category;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public int createCategory(CategoryCreateDto categoryCreateDto) {
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        return categoryRepository.save(category).getId();
    }

    @Override
    public CategoryQueryDto updateCategory(int id, CategoryUpdateDto categoryUpdateDto) {
        if (categoryRepository.findById(id).isPresent()) {
            Category category = categoryRepository.findById(id).get();
            category.setName(categoryUpdateDto.getName());
            Category updatedCategory = categoryRepository.save(category);
            return new CategoryQueryDto(updatedCategory.getId(), updatedCategory.getName());
        } else {
            return null;
        }
    }

    @Override
    public int deleteCategory(int id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}
