package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductCreateDto;
import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.dto.product.ProductUpdateDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements  ProductCommandService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductCommandServiceImpl(ProductRepository productRepository,
                                     CategoryRepository categoryRepository,
                                     ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int createProduct(ProductCreateDto productCreateDto) {
        boolean isCategoryPresent = categoryRepository.findById(productCreateDto.getCategoryId()).isPresent();
        if (isCategoryPresent) {
            Product product = modelMapper.map(productCreateDto, Product.class);
            product.setCategory(categoryRepository.findById(productCreateDto.getCategoryId()).get());
            return productRepository.save(product).getId();
        } else {
            return 0;
        }
    }

    @Override
    public ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto) {
        boolean isProductPresent = productRepository.findById(id).isPresent();
        boolean isCategoryPresent = categoryRepository.findById(productUpdateDto.getCategoryId()).isPresent();
        if (isProductPresent && isCategoryPresent) {
            Product product = modelMapper.map(productUpdateDto, Product.class);
            product.setId(id);
            product.setCategory(categoryRepository.findById(productUpdateDto.getCategoryId()).get());
            Product updatedProduct = productRepository.save(product);
            return modelMapper.map(updatedProduct, ProductQueryDto.class);
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
