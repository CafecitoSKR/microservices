package com.espino.orderservice.orders.application.internal.commandservices;

import com.espino.orderservice.orders.application.internal.outboundservices.inventory.InventoryService;
import com.espino.orderservice.orders.domain.model.aggregates.Order;
import com.espino.orderservice.orders.domain.model.commands.CreateOrderCommand;
import com.espino.orderservice.orders.domain.services.OrderCommandService;
import com.espino.orderservice.orders.infrastructure.persistence.jpa.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderCommandServiceImpl(OrderRepository orderRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Optional<Order> handle(CreateOrderCommand command) {
        //TODO: verificar el usuario
        Order order = new Order(command);
        var orderCreated = orderRepository.save(order);
        return Optional.of(orderCreated);
    }
}
