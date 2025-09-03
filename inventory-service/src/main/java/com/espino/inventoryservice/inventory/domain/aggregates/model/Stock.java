package com.espino.inventoryservice.inventory.domain.aggregates.model;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateStockCommand;
import com.espino.inventoryservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Stock extends AuditableAbstractAggregateRoot<Stock> {
    @NotNull
    private Long productId;

    private Long locationId;

    @Min(0)
    private int onHand;

    @Min(0)
    private int reserved;

    @Version
    private long version;

    protected Stock() {}

    public Stock(CreateStockCommand command){
        this.productId= command.productId();
        this.locationId = null;
        this.onHand = command.onHand();
        this.reserved = 0;
    }


}
