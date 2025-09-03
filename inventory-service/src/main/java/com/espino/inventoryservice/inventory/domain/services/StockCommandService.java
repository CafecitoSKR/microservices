package com.espino.inventoryservice.inventory.domain.services;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateStockCommand;
import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;

import java.util.Optional;

public interface StockCommandService {
    Optional<Stock> handle(CreateStockCommand command);
}
