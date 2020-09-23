package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductQueryServiceImpl implements  ProductQueryService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductQueryServiceImpl(ProductRepository productRepository,
                                   ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductQueryDto getProduct(int id) {
        if(productRepository.findById(id).isPresent()) {
            Product product = productRepository.findById(id).get();
            return modelMapper.map(product, ProductQueryDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<ProductQueryDto> getAllProducts() {
        List<ProductQueryDto> productsList = new ArrayList<>();
        productRepository.findAll().forEach(product ->
                productsList.add(modelMapper.map(product, ProductQueryDto.class)));
        return productsList;
    }
}
