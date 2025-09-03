package com.espino.productservice.catalog.domain.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private final Long productId;
    public ProductNotFoundException(Long productId) {
        super("Product not found with ID: %s".formatted(productId));
        this.productId = productId;
    }
}
