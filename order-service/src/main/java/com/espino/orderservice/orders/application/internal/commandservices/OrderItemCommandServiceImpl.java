    package com.espino.orderservice.orders.application.internal.commandservices;

    import brave.Tracer;
    import com.espino.orderservice.orders.application.internal.outboundservices.inventory.InventoryService;
    import com.espino.orderservice.orders.domain.model.commands.OrderItem.CreateOrderItemCommand;
    import com.espino.orderservice.orders.domain.model.entities.OrderItem;
    import com.espino.orderservice.orders.domain.model.events.OrderPlacedEvent;
    import com.espino.orderservice.orders.domain.services.OrderItemCommandService;
    import com.espino.orderservice.orders.infrastructure.persistence.jpa.repositories.OrderItemRepository;
    import com.espino.orderservice.orders.infrastructure.persistence.jpa.repositories.OrderRepository;
    import lombok.AllArgsConstructor;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Service;

    import java.util.Optional;

    @Service
    @AllArgsConstructor
    public class OrderItemCommandServiceImpl implements OrderItemCommandService {
        private final OrderRepository orderRepository;
        private final OrderItemRepository orderItemRepository;
        private final InventoryService inventoryService;
        private final Tracer tracer;
        private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

        @Override
        public Optional<OrderItem> handle(CreateOrderItemCommand command) {
            var availableQuantity = inventoryService.getAvailableStockQuantity(command.productId());
            if(availableQuantity <= 0) {
                throw new IllegalArgumentException("Product Stock is not available");
            }
            var order = orderRepository.findById(command.orderId());
            if(order.isEmpty()) {
                throw new IllegalArgumentException("Order Not Found");
            }
            var orderItem = new OrderItem(command);

            var orderItemAdded = order.get().addItem(orderItem);

            orderItemRepository.save(orderItemAdded);

            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(orderItem.getId()));

            return Optional.of(orderItemAdded);
        }
    }
