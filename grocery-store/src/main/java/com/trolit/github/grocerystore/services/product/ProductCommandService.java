package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.*;

import java.util.List;

public interface ProductCommandService {

    int createProduct(ProductCreateDto productCreateDto);

    ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto);

    int deleteProduct(int id);

    int buyProducts(List<String> productsWithQuantity);

    int setProductStock(int id, ProductStockOnlyDto productStockOnlyDto);

    int changeProductPriceByPercentage(int id, ProductPriceChangeDto productPriceChangeDto);

}
