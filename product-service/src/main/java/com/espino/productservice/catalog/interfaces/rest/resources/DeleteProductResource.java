package com.espino.productservice.catalog.interfaces.rest.resources;

public record DeleteProductResource(
        Long productId,
        String message
) {
}
