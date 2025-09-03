package com.espino.orderservice.orders.domain.services;

import com.espino.orderservice.orders.domain.model.aggregates.Order;
import com.espino.orderservice.orders.domain.model.commands.CreateOrderCommand;

import java.util.Optional;

public interface OrderCommandService {
    Optional<Order> handle(CreateOrderCommand command);
}
