package com.espino.productservice.catalog.domain.model.aggregates;

import com.espino.productservice.catalog.domain.model.commands.CreateProductCommand;
import com.espino.productservice.catalog.domain.model.commands.UpdateProductCommand;
import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;
import com.espino.productservice.catalog.domain.model.valueobjects.Sku;
import com.espino.productservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Product extends AuditableAbstractAggregateRoot<Product> {

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private double price;

    @NotBlank
    private String brand;

    @NotNull
    @Min(0)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus status;

    @Embedded
    private Sku sku;

    protected Product() {}

    public Product(CreateProductCommand command){
        this.name=command.name();
        this.price=command.price();
        this.brand=command.brand();
        this.quantity=command.quantity();
        this.sku = new Sku(command.sku());
        this.status = ProductStatus.ACTIVE;
    }
    public void update(UpdateProductCommand command){
        this.name=command.name();
        this.price=command.price();
        this.brand=command.brand();
        this.quantity=command.quantity();
        this.sku= new Sku(command.sku());
        this.status = ProductStatus.ACTIVE;
        this.status = ProductStatus.valueOf(command.status());
    }

}
