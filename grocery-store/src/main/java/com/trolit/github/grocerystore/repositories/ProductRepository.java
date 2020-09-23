package com.trolit.github.grocerystore.repositories;

import com.trolit.github.grocerystore.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
