package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.ProductCreateDto;
import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.dto.product.ProductStockOnlyDto;
import com.trolit.github.grocerystore.dto.product.ProductUpdateDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

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

    @Override
    public int buyProducts(List<String> productsWithQuantity) {
        boolean isOrderPossible = checkIfProductsOrderIsPossible(productsWithQuantity);
        if (isOrderPossible) {
            for (int i = 0; i < productsWithQuantity.size(); i++) {
                if (i + 1 >= productsWithQuantity.size()) {
                    break;
                }
                int productId = parseInt(productsWithQuantity.get(i));
                int quantity = parseInt(productsWithQuantity.get(i + 1));
                updateProductStock(productId, quantity);
            }
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int setProductStock(int id, ProductStockOnlyDto productStockOnlyDto) {
        boolean isProductPresent = productRepository.findById(id).isPresent();
        if (isProductPresent) {
            Product product = productRepository.findById(id).get();
            product.setStock(productStockOnlyDto.getStock());
            productRepository.save(product);
            return 1;
        } else {
            return 0;
        }
    }

    private int getCurrentStockValue(int productStock, int quantity) {
        return productStock - quantity;
    }

    private boolean checkIfProductsOrderIsPossible(List<String> productsWithQuantity) {
        boolean flag = false;
        for (int i = 0; i < productsWithQuantity.size(); i++) {
            if (i + 1 >= productsWithQuantity.size()) {
                break;
            }
            int productId = parseInt(productsWithQuantity.get(i));
            int quantity = parseInt(productsWithQuantity.get(i + 1));
            if (quantity < 0) {
                flag = false;
                break;
            }
            boolean isProductPresent = productRepository.findById(productId).isPresent();
            if (isProductPresent) {
                Product product = productRepository.findById(productId).get();
                int currentStockValue = getCurrentStockValue(product.getStock(), quantity);
                if (currentStockValue >= 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void updateProductStock(int productId, int quantity) {
        Product product = productRepository.findById(productId).get();
        int currentStock = product.getStock() - quantity;
        product.setStock(currentStock);
        productRepository.save(product);
    }
}
