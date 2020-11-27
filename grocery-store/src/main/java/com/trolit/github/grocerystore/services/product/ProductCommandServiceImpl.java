package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.dto.product.*;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.models.Category;
import com.trolit.github.grocerystore.repositories.CategoryRepository;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.trolit.github.grocerystore.services.product.ProductCommonMethods.getPriceStatus;
import static com.trolit.github.grocerystore.services.product.ProductCommonMethods.returnPercentageDiffBetweenPrices;
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
            product.setCategory(getCategoryById(productCreateDto.getCategoryId()));
            return productRepository.save(product).getId();
        } else {
            return 0;
        }
    }

    @Override
    public ProductQueryDto updateProduct(int id, ProductUpdateDto productUpdateDto) {
        boolean isCategoryPresent = categoryRepository.findById(productUpdateDto.getCategoryId()).isPresent();
        if (isProductPresent(id) && isCategoryPresent) {
            BigDecimal currentPrice = getCurrentPrice(id);
            BigDecimal newPrice = productUpdateDto.getPrice();
            Product product = modelMapper.map(productUpdateDto, Product.class);
            if (!newPrice.equals(currentPrice)) {
                product.setPreviousPrice(currentPrice);
            }
            product.setId(id);
            product.setCategory(getCategoryById(productUpdateDto.getCategoryId()));
            Product updatedProduct = productRepository.save(product);
            ProductQueryDto productQueryDto = modelMapper.map(updatedProduct, ProductQueryDto.class);
            productQueryDto.setPercentagePriceDiff(returnPercentageDiffBetweenPrices(newPrice, currentPrice));
            productQueryDto.setPriceStatus(getPriceStatus(newPrice, currentPrice));
            return productQueryDto;
        } else {
            return null;
        }
    }

    @Override
    public int deleteProduct(int id) {
        if (isProductPresent(id)) {
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
            for (int i = 0; i < productsWithQuantity.size(); i+=2) {
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
        if (isProductPresent(id)) {
            Product product = getProductById(id);
            product.setStock(productStockOnlyDto.getStock());
            productRepository.save(product);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public UpdatedProductPriceDto changeProductPriceByPercentage(int id, ProductPriceChangeDto productPriceChangeDto) {
        if (isProductPresent(id)) {
            BigDecimal currentPrice = getCurrentPrice(id);
            Product product = getProductById(id);
            BigDecimal newPrice = product.getPrice()
                    .multiply(new BigDecimal(productPriceChangeDto.getPercentage()))
                    .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            product.setPreviousPrice(currentPrice);
            product.setPrice(newPrice);
            productRepository.save(product);
            return new UpdatedProductPriceDto(id, newPrice, currentPrice,
                    returnPercentageDiffBetweenPrices(newPrice, currentPrice),
                    getPriceStatus(newPrice, currentPrice));
        } else {
            return new UpdatedProductPriceDto();
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private BigDecimal getCurrentPrice(int id) {
        return productRepository.findById(id).get().getPrice();
    }

    private boolean isProductPresent(int id) {
        return productRepository.findById(id).isPresent();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Category getCategoryById(int id) {
        return categoryRepository.findById(id).get();
    }

    private int getCurrentStockValue(int productStock, int quantity) {
        return productStock - quantity;
    }

    private boolean checkIfProductsOrderIsPossible(List<String> productsWithQuantity) {
        boolean flag = false;
        for (int i = 0; i < productsWithQuantity.size(); i+=2) {
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
