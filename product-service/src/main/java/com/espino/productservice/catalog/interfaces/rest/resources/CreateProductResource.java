package com.espino.productservice.catalog.interfaces.rest.resources;

public record CreateProductResource(
        String name,
        double price,
        String brand,
        int quantity,
        String sku
) {
}
