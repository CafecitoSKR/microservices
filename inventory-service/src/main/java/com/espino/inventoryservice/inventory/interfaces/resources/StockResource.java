package com.espino.inventoryservice.inventory.interfaces.resources;


public record StockResource(
        Long id,
        Long productId,
        Long locationId,
        int onHand,
        int reserved,
        long version

) {
}
