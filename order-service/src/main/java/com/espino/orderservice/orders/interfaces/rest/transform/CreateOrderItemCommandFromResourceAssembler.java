package com.espino.orderservice.orders.interfaces.rest.transform;

import com.espino.orderservice.orders.domain.model.commands.OrderItem.CreateOrderItemCommand;
import com.espino.orderservice.orders.interfaces.rest.resources.CreateOrderItemResource;

public class CreateOrderItemCommandFromResourceAssembler {
    public static CreateOrderItemCommand fromResource(CreateOrderItemResource resource){
        return new CreateOrderItemCommand(
                resource.orderId(),
                resource.productId(),
                resource.quantity(),
                resource.unitPrice(),
                resource.sku(),
                resource.productName()
        );
    }
}
