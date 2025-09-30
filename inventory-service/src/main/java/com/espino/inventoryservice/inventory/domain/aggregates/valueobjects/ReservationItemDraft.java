package com.espino.inventoryservice.inventory.domain.aggregates.valueobjects;

public record ReservationItemDraft(
        Long productId,
        int quantity
) {
}
