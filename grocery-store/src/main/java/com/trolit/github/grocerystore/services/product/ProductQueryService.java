package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;

import java.util.List;

public interface ProductQueryService {
    public ProductQueryDto getProduct(int id);

    public List<ProductQueryDto> getAllProducts();
}
