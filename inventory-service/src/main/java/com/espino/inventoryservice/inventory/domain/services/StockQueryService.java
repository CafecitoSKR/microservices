package com.espino.inventoryservice.inventory.domain.services;

import com.espino.inventoryservice.inventory.domain.aggregates.queries.GetQuantityByProductIdQuery;


public interface StockQueryService {
    Integer handle(GetQuantityByProductIdQuery query);
}
