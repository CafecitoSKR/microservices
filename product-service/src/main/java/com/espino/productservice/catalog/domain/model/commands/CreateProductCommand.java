package com.espino.productservice.catalog.domain.model.commands;

public record CreateProductCommand(
        String name,
        double price,
        String brand,
        int quantity,
        String sku
) {
}
