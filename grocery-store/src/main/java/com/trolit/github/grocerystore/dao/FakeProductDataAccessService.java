package com.trolit.github.grocerystore.dao;

import com.trolit.github.grocerystore.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("fakeDao")
public class FakeProductDataAccessService implements ProductDao {

    private static List<Product> DB = new ArrayList<Product>();

    @Override
    public int addProduct(UUID id, Product product) {
        DB.add(new Product(id, product.getName()));
        return 1;
    }

    @Override
    public List<Product> selectAllProducts() {
        return DB;
    }
}
