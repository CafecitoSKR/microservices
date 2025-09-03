package com.espino.orderservice.orders.interfaces.rest.transform;

import com.espino.orderservice.orders.domain.model.entities.OrderItem;
import com.espino.orderservice.orders.interfaces.rest.resources.OrderItemResource;

public class OrderItemResourceFromEntityAssembler {
    public static OrderItemResource fromEntity(OrderItem entity){
        return new OrderItemResource(
                entity.getId(),
                entity.getOrder().getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getProductSku(),
                entity.getProductName()
        );
    }
}
