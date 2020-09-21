package com.trolit.github.grocerystore.dao;

import com.trolit.github.grocerystore.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDao {

    int addProduct(UUID id, Product product);

    default int addProduct(Product product) {
        UUID id = UUID.randomUUID();
        return addProduct(id, product);
    }

    List<Product> selectAllProducts();
}
