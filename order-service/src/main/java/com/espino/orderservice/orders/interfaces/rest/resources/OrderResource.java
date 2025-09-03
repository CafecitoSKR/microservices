package com.espino.orderservice.orders.interfaces.rest.resources;

import java.math.BigDecimal;

public record OrderResource(
        Long orderId,
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
