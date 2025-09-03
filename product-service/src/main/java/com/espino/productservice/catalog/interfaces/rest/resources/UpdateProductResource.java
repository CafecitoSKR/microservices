package com.espino.productservice.catalog.interfaces.rest.resources;


import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;

public record UpdateProductResource(
        String name,
        double price,
        String brand,
        int quantity,
        String sku,
        ProductStatus status
) {
}
