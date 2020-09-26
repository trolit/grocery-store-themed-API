package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;
import com.trolit.github.grocerystore.models.Category;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryCommandServiceImpl(CategoryRepository categoryRepository,
                                      ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int createCategory(CategoryCreateDto categoryCreateDto) {
        return categoryRepository.save(
                modelMapper.map(categoryCreateDto, Category.class))
                .getId();
    }

    @Override
    public CategoryQueryDto updateCategory(int id, CategoryUpdateDto categoryUpdateDto) {
        if (isCategoryPresent(id)) {
            Category category = modelMapper.map(categoryUpdateDto, Category.class);
            category.setId(id);
            Category updatedCategory = categoryRepository.save(category);
            return modelMapper.map(updatedCategory, CategoryQueryDto.class);
        } else {
            return null;
        }
    }

    @Override
    public int deleteCategory(int id) {
        if (isCategoryPresent(id)) {
            categoryRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isCategoryPresent(int id) {
        return categoryRepository.findById(id).isPresent();
    }

}
