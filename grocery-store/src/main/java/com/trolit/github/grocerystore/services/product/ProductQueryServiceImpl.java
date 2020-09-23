package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductQueryServiceImpl implements  ProductQueryService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductQueryDto getProduct(int id) {
        if(productRepository.findById(id).isPresent()) {
            Product product = productRepository.findById(id).get();
            return new ProductQueryDto(product.getId(), product.getName());
        } else {
            return null;
        }
    }

    @Override
    public List<ProductQueryDto> getAllProducts() {
        List<ProductQueryDto> productsList = new ArrayList<>();

        productRepository.findAll().forEach(product ->
                productsList.add(new ProductQueryDto(product.getId(), product.getName())));

        return productsList;
    }
}
