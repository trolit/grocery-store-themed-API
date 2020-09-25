package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;

import java.util.List;

public interface ProductQueryService {

    ProductQueryDto getProduct(int id);

    List<ProductQueryDto> getAllProducts(String search);

}
