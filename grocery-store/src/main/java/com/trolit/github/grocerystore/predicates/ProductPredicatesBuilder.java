package com.trolit.github.grocerystore.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.trolit.github.grocerystore.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// https://www.baeldung.com/rest-api-search-language-spring-data-querydsl

public class ProductPredicatesBuilder {

    private final List<SearchCriteria> params;

    public ProductPredicatesBuilder() {
        params = new ArrayList<>();
    }

    public ProductPredicatesBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }

        final List<BooleanExpression> predicates = params.stream().map(param -> {
            ProductPredicate predicate = new ProductPredicate(param);
            String key = param.getKey();
            return switch (key) {
                case "priceStatus" -> predicate.getPriceStatusPredicate();
                case "categoryId" -> predicate.getCategoryIdPredicate();
                case "category" -> predicate.getCategoryNamePredicate();
                default -> predicate.getPredicate();
            };
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

    public Integer getParamsSize() {
        return params.size();
    }

}
