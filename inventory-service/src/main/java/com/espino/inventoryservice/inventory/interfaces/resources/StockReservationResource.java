package com.espino.inventoryservice.inventory.interfaces.resources;


import java.util.List;

public record StockReservationResource(
        Long orderId,
        List<ReservationItemResource> items
) {
}
