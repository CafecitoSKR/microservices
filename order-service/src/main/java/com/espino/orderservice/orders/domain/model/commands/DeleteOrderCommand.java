package com.espino.orderservice.orders.domain.model.commands;

public record DeleteOrderCommand(
        Long orderId
) {
}
