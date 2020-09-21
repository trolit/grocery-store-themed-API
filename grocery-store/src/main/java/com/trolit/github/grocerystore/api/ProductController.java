package com.trolit.github.grocerystore.api;

import com.trolit.github.grocerystore.model.Product;
import com.trolit.github.grocerystore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct(Product product) {
        productService.addProduct(product);
    }
}
