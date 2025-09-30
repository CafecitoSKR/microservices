package com.espino.inventoryservice.inventory.domain.services;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateReservationCommand;
import com.espino.inventoryservice.inventory.domain.aggregates.model.StockReservation;

import java.util.Optional;

public interface StockReservationCommandService {
    Optional<StockReservation> handle(CreateReservationCommand command);
}
