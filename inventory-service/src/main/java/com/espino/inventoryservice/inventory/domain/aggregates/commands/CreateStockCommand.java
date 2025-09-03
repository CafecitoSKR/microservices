package com.espino.inventoryservice.inventory.domain.aggregates.commands;


public record CreateStockCommand(Long productId,
                                 int onHand) {

}
