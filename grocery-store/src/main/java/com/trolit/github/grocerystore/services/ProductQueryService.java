package com.trolit.github.grocerystore.services;

import com.trolit.github.grocerystore.dto.ProductQueryDto;

import java.util.List;

public interface ProductQueryService {
    public ProductQueryDto getProduct(int id);

    public List<ProductQueryDto> getAllProducts();
}
