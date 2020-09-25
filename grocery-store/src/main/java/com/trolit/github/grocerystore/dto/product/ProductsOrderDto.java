package com.trolit.github.grocerystore.dto.product;

import java.util.ArrayList;
import java.util.List;

public class ProductsOrderDto {

    private List<String> order = new ArrayList<>();

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }
}
