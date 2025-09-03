package com.espino.orderservice.orders.application.internal.outboundservices.inventory;

public interface InventoryService {
    int getAvailableStockQuantity(Long productId);
}
