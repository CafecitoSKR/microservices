package com.espino.orderservice.orders.domain.model.commands.OrderItem;

import com.espino.orderservice.orders.domain.model.valueobjects.Money;
import com.espino.orderservice.orders.domain.model.valueobjects.Sku;

public record CreateOrderItemCommand(
        Long orderId,
        Long productId,
        Integer quantity,
        Money unitPrice,
        Sku sku,
        String productName

) {
}
