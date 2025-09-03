package com.espino.orderservice.orders.domain.model.aggregates;

import com.espino.orderservice.orders.domain.model.commands.CreateOrderCommand;
import com.espino.orderservice.orders.domain.model.entities.OrderItem;
import com.espino.orderservice.orders.domain.model.valueobjects.Address;
import com.espino.orderservice.orders.domain.model.valueobjects.Money;
import com.espino.orderservice.orders.domain.model.valueobjects.OrderStatus;
import com.espino.orderservice.orders.domain.model.valueobjects.PaymentStatus;
import com.espino.orderservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Order extends AuditableAbstractAggregateRoot<Order> {
    @NotNull
    private Long customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "money_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "money_amount"))
            })
    private Money money;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "address_number")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))})
    private Address address;

    @NotNull
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    @Version
    private long version;


    protected Order() {}

    public Order(Long customerId, String currency, BigDecimal amount,
                 String street,
                 String number,
                 String city,
                 String postalCode,
                 String country) {
        this.customerId = customerId;
        this.paymentStatus = PaymentStatus.PENDING;
        this.orderStatus = OrderStatus.CREATED;
        this.money = new Money(currency, amount);
        this.address = new Address(street, number, city, postalCode, country);
    }

    public Order(CreateOrderCommand command){
        this.customerId=command.customerId();
        this.money= new Money(command.currency(),command.amount());
        this.paymentStatus = PaymentStatus.PENDING;
        this.orderStatus = OrderStatus.CREATED;
        this.address = new Address(command.street(),command.number(),command.city(),command.postalCode(),command.country());

    }
    public OrderItem addItem(OrderItem item){
        items.add(item);
        item.setOrder(this);
        return item;
    }
}
