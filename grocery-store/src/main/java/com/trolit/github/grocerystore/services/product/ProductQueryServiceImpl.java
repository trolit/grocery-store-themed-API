package com.trolit.github.grocerystore.services.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.models.Product;
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
        if (search != null) {
            ProductPredicatesBuilder productPredicatesBuilder = new ProductPredicatesBuilder();
            addParamsToPredicatesBuilder(productPredicatesBuilder, search);
            BooleanExpression productExpression = productPredicatesBuilder.build();
            result = productRepository.findAll(productExpression);
        } else {
            result = productRepository.findAll();
        }
        for (Product product: result) {
            ProductQueryDto productQueryDto = modelMapper.map(product, ProductQueryDto.class);
            BigDecimal previousPrice = product.getPreviousPrice();
            setProductQueryDtoPercentageDiff(productQueryDto, product.getPrice(), previousPrice);
            productQueryDto.setPriceStatus(getPriceStatus(product.getPrice(), previousPrice));
            productsList.add(productQueryDto);
        }
        return productsList;
    }

    private void setProductQueryDtoPercentageDiff(ProductQueryDto productQueryDto,
                                                  BigDecimal currentPrice,
                                                  BigDecimal previousPrice) {
        if (previousPrice != null && previousPrice.signum() > 0) {
            int percentageDiff =
                    returnPercentageDiffBetweenPrices(currentPrice, previousPrice);
            productQueryDto.setPercentagePriceDiff(percentageDiff);
        } else {
            productQueryDto.setPercentagePriceDiff(0);
        }
    }

    private void addParamsToPredicatesBuilder(ProductPredicatesBuilder productPredicatesBuilder,
                                                                  String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?|\\w+?%20\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            String key = matcher.group(1);
            String operation = matcher.group(2);
            String value = matcher.group(3);
            productPredicatesBuilder.with(key, operation, value);
        }
    }
}
