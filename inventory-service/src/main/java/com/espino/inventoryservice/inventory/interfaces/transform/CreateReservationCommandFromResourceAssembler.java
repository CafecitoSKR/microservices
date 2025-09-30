package com.espino.inventoryservice.inventory.interfaces.transform;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateReservationCommand;
import com.espino.inventoryservice.inventory.domain.aggregates.valueobjects.ReservationItemDraft;
import com.espino.inventoryservice.inventory.interfaces.resources.CreateReservationResource;

public class CreateReservationCommandFromResourceAssembler {
    public static CreateReservationCommand toCommand(CreateReservationResource resource){
        return new CreateReservationCommand(
                resource.orderId(),
                resource.items().stream()
                        .map(d -> new ReservationItemDraft(d.productId(), d.quantity()))
                        .toList()
        );
    }
}
