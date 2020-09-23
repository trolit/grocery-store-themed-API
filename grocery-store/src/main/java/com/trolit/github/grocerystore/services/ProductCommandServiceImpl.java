package com.trolit.github.grocerystore.services;

import com.trolit.github.grocerystore.dto.ProductCreateDto;
import com.trolit.github.grocerystore.dto.ProductQueryDto;
import com.trolit.github.grocerystore.dto.ProductUpdateDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements  ProductCommandService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int createProduct(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        return productRepository.save(product).getId();
    }

    @Override
    public ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto) {
        if (productRepository.findById(id).isPresent()) {
            Product product = productRepository.findById(id).get();
            product.setName(productUpdateDto.getName());
            Product updatedProduct = productRepository.save(product);
            return new ProductQueryDto(updatedProduct.getId(), updatedProduct.getName());
        } else {
            return null;
        }
    }

    @Override
    public int deleteProduct(int id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}
