package com.espino.orderservice.orders.domain.model.entities;

import com.espino.orderservice.orders.domain.model.aggregates.Order;
import com.espino.orderservice.orders.domain.model.commands.OrderItem.CreateOrderItemCommand;
import com.espino.orderservice.orders.domain.model.valueobjects.Money;
import com.espino.orderservice.orders.domain.model.valueobjects.Sku;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Order order;


    @NotNull
    private Long productId;

    @NotNull
    @Embedded
    private Sku productSku;

    @NotNull
    private String productName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "unit_price_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "unit_price_amount"))
    })
    private Money unitPrice;

    @Min(1)
    private int quantity;

    @Version
    private long version;

    protected OrderItem() {}


    public OrderItem( Long productId, Sku productSku, String productName, Money unitPrice, int quantity) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    public OrderItem(CreateOrderItemCommand cmd){
        this.productId = cmd.productId();
        this.productSku = cmd.sku();
        this.productName = cmd.productName();
        this.unitPrice = cmd.unitPrice();
        this.quantity = cmd.quantity();
    }
}
