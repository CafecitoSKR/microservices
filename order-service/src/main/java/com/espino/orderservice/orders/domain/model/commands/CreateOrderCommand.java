package com.espino.orderservice.orders.domain.model.commands;


import java.math.BigDecimal;

public record CreateOrderCommand(
        Long customerId,
        String currency,
        BigDecimal amount,
        String street,
        String number,
        String city,
        String postalCode,
        String country
) {

}
