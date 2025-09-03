package com.espino.orderservice.orders.interfaces.rest.resources;

import com.espino.orderservice.orders.domain.model.valueobjects.Money;
import com.espino.orderservice.orders.domain.model.valueobjects.Sku;

public record OrderItemResource(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity,
        Money unitPrice,
        Sku sku,
        String productName
) {
}
