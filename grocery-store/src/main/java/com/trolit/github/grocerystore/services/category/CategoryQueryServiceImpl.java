package com.trolit.github.grocerystore.services.category;

import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.models.Category;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryQueryServiceImpl(CategoryRepository categoryRepository,
                                    ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryQueryDto getCategory(int id) {
        if(categoryRepository.findById(id).isPresent()) {
            Category category = categoryRepository.findById(id).get();
            return modelMapper.map(category, CategoryQueryDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<CategoryQueryDto> getAllCategories() {
        List<CategoryQueryDto> categoriesList = new ArrayList<>();
        categoryRepository.findAll().forEach(category ->
                categoriesList.add(modelMapper.map(category, CategoryQueryDto.class)));
        return categoriesList;
    }

}
