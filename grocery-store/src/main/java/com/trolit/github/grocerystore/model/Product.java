package com.trolit.github.grocerystore.model;

import java.util.UUID;

public class Product {
    private final UUID id;
    private final String name;

    public Product(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
