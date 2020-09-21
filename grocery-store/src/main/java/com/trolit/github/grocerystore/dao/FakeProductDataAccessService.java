package com.trolit.github.grocerystore.dao;

import com.trolit.github.grocerystore.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Product> selectProductById(UUID id) {
        return DB.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteProductById(UUID id) {
        Optional<Product> productMaybe = selectProductById(id);
        if (productMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(productMaybe.get());
        return 1;
    }

    @Override
    public int updateProductById(UUID id, Product productUpdate) {
        return selectProductById(id)
                .map(product -> {
                    int indexOfProductToUpdate = DB.indexOf(product);
                    if(indexOfProductToUpdate >= 0) {
                        DB.set(indexOfProductToUpdate, new Product(id, productUpdate.getName()));
                        return 1;
                    } else {
                        return 0;
                    }
                })
                .orElse(0);
    }
}
