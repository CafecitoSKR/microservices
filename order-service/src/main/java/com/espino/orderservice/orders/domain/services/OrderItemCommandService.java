package com.espino.orderservice.orders.domain.services;

import com.espino.orderservice.orders.domain.model.commands.OrderItem.CreateOrderItemCommand;
import com.espino.orderservice.orders.domain.model.entities.OrderItem;

import java.util.Optional;

public interface OrderItemCommandService {
    Optional<OrderItem> handle(CreateOrderItemCommand command);
}
