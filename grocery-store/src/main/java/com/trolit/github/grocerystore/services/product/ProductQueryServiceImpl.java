package com.trolit.github.grocerystore.services.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.models.QProduct;
import com.trolit.github.grocerystore.predicates.ProductPredicatesBuilder;
import com.trolit.github.grocerystore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.trolit.github.grocerystore.services.product.ProductCommonMethods.getPriceStatus;
import static com.trolit.github.grocerystore.services.product.ProductCommonMethods.returnPercentageDiffBetweenPrices;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

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
            ProductQueryDto productQueryDto = modelMapper.map(product, ProductQueryDto.class);
            productQueryDto.setPercentagePriceDiff(
                    returnPercentageDiffBetweenPrices(product.getPrice(), product.getPreviousPrice()));
            productQueryDto.setPriceStatus(getPriceStatus(product.getPrice(), product.getPreviousPrice()));
            return productQueryDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ProductQueryDto> getAllProducts(String search) {
        List<ProductQueryDto> productsList = new ArrayList<>();
        Iterable<Product> result;
        int categoryId = 0;
        String categoryName = "";
        if (search != null) {
            // percentagePriceDiff key is NA
            if (search.contains("percentagePriceDiff")) {
                return productsList;
            }
            ProductPredicatesBuilder builder = new ProductPredicatesBuilder();
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?|\\w+?%20\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                String key = matcher.group(1);
                String operation = matcher.group(2);
                String value = matcher.group(3);
                switch (key) {
                    case "categoryId" -> categoryId = Integer.parseInt(value);
                    case "category" -> categoryName = convertWhiteSpaceEncoding(value);
                    default -> builder.with(key, operation, value);
                }
            }
            BooleanExpression productExpression = builder.build();
            if (categoryId > 0 || !categoryName.isEmpty()) {
                result = productRepository.findAll(
                        getExtendedExpression(builder, productExpression, categoryId, categoryName));
            } else {
                result = productRepository.findAll(productExpression);
            }
        } else {
            result = productRepository.findAll();
        }
        for (Product product: result) {
            ProductQueryDto productQueryDto = modelMapper.map(product, ProductQueryDto.class);
            BigDecimal previousPrice = product.getPreviousPrice();
            if (previousPrice != null && previousPrice.signum() > 0) {
                int percentageDiff =
                        returnPercentageDiffBetweenPrices(product.getPrice(), product.getPreviousPrice());
                productQueryDto.setPercentagePriceDiff(percentageDiff);
            } else {
                productQueryDto.setPercentagePriceDiff(0);
            }
            productQueryDto.setPriceStatus(getPriceStatus(product.getPrice(), previousPrice));
            productsList.add(productQueryDto);
        }
        return productsList;
    }

    private String convertWhiteSpaceEncoding(String value) {
        return value.replace("%20", " ");
    }

    private BooleanExpression getExtendedExpression(ProductPredicatesBuilder builder,
                                                    BooleanExpression productExpression,
                                                    Integer categoryId,
                                                    String categoryName) {
        BooleanExpression categoryExpression = getCategoryExpression(categoryId, categoryName);

        if (builder.getParamsSize() > 0) {
            return productExpression.and(categoryExpression);
        } else {
            return categoryExpression;
        }
    }

    private BooleanExpression getCategoryExpression(Integer categoryId,
                                                    String categoryName) {
        if (categoryId > 0 && categoryName.isEmpty()) {
            return categoryIdExpression(categoryId);
        } else if (categoryId == 0 && !categoryName.isEmpty()) {
            return categoryNameExpression(categoryName);
        } else {
            return categoryIdExpression(categoryId).and(categoryNameExpression(categoryName));
        }
    }

    private BooleanExpression categoryIdExpression(Integer categoryId) {
        QProduct product = QProduct.product;
        return product.category.id.eq(categoryId);
    }

    private BooleanExpression categoryNameExpression(String categoryName) {
        QProduct product = QProduct.product;
        return product.category.name.eq(categoryName);
    }

}
