package com.espino.productservice.catalog.domain.model.commands;

public record UpdateProductCommand(
        Long productId,
        String name,
        double price,
        String brand,
        int quantity,
        String sku,
        String status
) {
}
