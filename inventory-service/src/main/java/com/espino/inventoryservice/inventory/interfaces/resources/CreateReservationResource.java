package com.espino.inventoryservice.inventory.interfaces.resources;


import java.util.List;

public record CreateReservationResource(
        Long orderId,
        List<ReservationItemResource> items
) {
}
