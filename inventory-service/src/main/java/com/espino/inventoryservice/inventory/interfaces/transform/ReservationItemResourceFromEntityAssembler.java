package com.espino.inventoryservice.inventory.interfaces.transform;

import com.espino.inventoryservice.inventory.domain.aggregates.entities.ReservationItem;
import com.espino.inventoryservice.inventory.interfaces.resources.ReservationItemResource;

public class ReservationItemResourceFromEntityAssembler {
    public static ReservationItemResource toResource(ReservationItem entity){
        return new ReservationItemResource(
                entity.getProductId(),
                entity.getQuantity()
        );
    }
}
