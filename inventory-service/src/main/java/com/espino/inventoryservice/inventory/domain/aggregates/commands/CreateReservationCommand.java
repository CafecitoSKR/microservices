package com.espino.inventoryservice.inventory.domain.aggregates.commands;

import com.espino.inventoryservice.inventory.domain.aggregates.valueobjects.ReservationItemDraft;

import java.util.List;

public record CreateReservationCommand(
        Long orderId,
        List<ReservationItemDraft> items
) {
}
