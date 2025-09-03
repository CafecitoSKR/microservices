package com.espino.inventoryservice.inventory.interfaces.transform;

import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;
import com.espino.inventoryservice.inventory.interfaces.resources.StockResource;

public class StockResourceFromEntityAssembler {
    public static StockResource fromEntity(Stock entity){
        return new StockResource(entity.getId(),
                entity.getProductId(),
                entity.getLocationId(),
                entity.getOnHand(),
                entity.getReserved(),
                entity.getVersion());
    }
}
