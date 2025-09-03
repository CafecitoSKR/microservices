package com.espino.inventoryservice.inventory.interfaces.transform;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateStockCommand;
import com.espino.inventoryservice.inventory.interfaces.resources.CreateStockResource;

public class CreateStockCommandFromResourceAssembler {
    public static CreateStockCommand fromResource(CreateStockResource resource) {
        return new CreateStockCommand(resource.productId(), resource.onHand());
    }
}
