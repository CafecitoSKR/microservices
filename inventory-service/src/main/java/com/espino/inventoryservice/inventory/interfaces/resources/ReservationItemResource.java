package com.espino.inventoryservice.inventory.interfaces.resources;

public record ReservationItemResource(
        Long productId,
        int quantity
) {

}
