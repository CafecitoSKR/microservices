package com.espino.inventoryservice.inventory.interfaces.transform;

import com.espino.inventoryservice.inventory.domain.aggregates.model.StockReservation;
import com.espino.inventoryservice.inventory.interfaces.resources.StockReservationResource;

public class StockReservationResourceFromEntityAssembler {
    public static StockReservationResource toResource(StockReservation entity){
        return new StockReservationResource(
                entity.getOrderId(),
                entity.getReservationItems().stream().map(ReservationItemResourceFromEntityAssembler::toResource).toList()
        );
    }
}
