package com.espino.orderservice.orders.interfaces.rest.transform;

import com.espino.orderservice.orders.domain.model.aggregates.Order;
import com.espino.orderservice.orders.interfaces.rest.resources.OrderResource;

public class OrderResourceFromEntityAssembler {
    public static OrderResource fromEntity(Order entity){
        return new OrderResource(
                entity.getId(),
                entity.getCustomerId(),
                entity.getMoney().currency(),
                entity.getMoney().amount(),
                entity.getAddress().street(),
                entity.getAddress().number(),
                entity.getAddress().city(),
                entity.getAddress().postalCode(),
                entity.getAddress().country()
        );
    }
}
