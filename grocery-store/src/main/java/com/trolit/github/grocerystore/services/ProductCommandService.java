package com.trolit.github.grocerystore.services;

import com.trolit.github.grocerystore.dto.ProductCreateDto;
import com.trolit.github.grocerystore.dto.ProductQueryDto;
import com.trolit.github.grocerystore.dto.ProductUpdateDto;

public interface ProductCommandService {
    public int createProduct(ProductCreateDto productCreateDto);

    public ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto);

    public int deleteProduct(int id);
}
