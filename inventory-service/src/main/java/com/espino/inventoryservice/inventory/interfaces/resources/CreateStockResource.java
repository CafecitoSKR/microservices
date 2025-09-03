package com.espino.inventoryservice.inventory.interfaces.resources;

public record CreateStockResource(
        Long productId,
        int onHand

) {
}
