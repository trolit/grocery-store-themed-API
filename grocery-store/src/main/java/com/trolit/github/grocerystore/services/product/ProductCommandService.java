package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductCreateDto;
import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.dto.product.ProductUpdateDto;

public interface ProductCommandService {
    public int createProduct(ProductCreateDto productCreateDto);

    public ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto);

    public int deleteProduct(int id);
}
