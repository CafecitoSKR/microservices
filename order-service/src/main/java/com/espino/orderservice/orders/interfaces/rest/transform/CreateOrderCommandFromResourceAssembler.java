package com.espino.orderservice.orders.interfaces.rest.transform;

import com.espino.orderservice.orders.domain.model.commands.CreateOrderCommand;
import com.espino.orderservice.orders.interfaces.rest.resources.CreateOrderResource;

public class CreateOrderCommandFromResourceAssembler {
    public static CreateOrderCommand fromResource(CreateOrderResource resource) {
        return new CreateOrderCommand(
                resource.customerId(),
                resource.currency(),
                resource.amount(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country()
        );
    }
}
