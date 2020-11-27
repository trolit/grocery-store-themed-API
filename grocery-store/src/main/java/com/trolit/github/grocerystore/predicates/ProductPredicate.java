package com.trolit.github.grocerystore.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.trolit.github.grocerystore.SearchCriteria;
import com.trolit.github.grocerystore.enums.PriceStatusEnum;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.models.QProduct;

public class ProductPredicate {

    private SearchCriteria criteria;

    public ProductPredicate(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public BooleanExpression getPredicate() {
        PathBuilder<Product> entityPath = new PathBuilder<>(Product.class, "product");

        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.gt(value);
                case "<":
                    return path.lt(value);
            }
        }
        else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    public BooleanExpression getPriceStatusPredicate() {
        QProduct product = QProduct.product;
        if (criteria.getValue().equals(PriceStatusEnum.rise.toString())) {
            return product.price.goe(product.previousPrice);
        } else if (criteria.getValue().equals(PriceStatusEnum.discount.toString())) {
            return product.price.loe(product.previousPrice);
        } else {
            return product.price.eq(product.previousPrice);
        }
    }

    public BooleanExpression getCategoryNamePredicate() {
        QProduct product = QProduct.product;
        String categoryName = convertWhiteSpaceEncoding(criteria.getValue().toString());
        return product.category.name.eq(categoryName);
    }

    private String convertWhiteSpaceEncoding(String value) {
        return value.replace("%20", " ");
    }

    public BooleanExpression getCategoryIdPredicate() {
        QProduct product = QProduct.product;
        Integer categoryId = Integer.parseInt(criteria.getValue().toString());
        return product.category.id.eq(categoryId);
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

}