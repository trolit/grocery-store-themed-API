package com.trolit.github.grocerystore.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.trolit.github.grocerystore.models.Product;
import com.trolit.github.grocerystore.models.QProduct;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>,
        QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {

    @SuppressWarnings("NullableProblems")
    @Override
    default void customize(final QuerydslBindings bindings, final QProduct root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
