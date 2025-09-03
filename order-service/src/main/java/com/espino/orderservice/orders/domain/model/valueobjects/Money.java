package com.espino.orderservice.orders.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record Money(
        String currency,
        BigDecimal amount
) {
    public Money() {
        this(null,null);
    }
    public Money{
        if(currency == null || currency.isEmpty()){
            throw  new IllegalArgumentException("currency cannot be null or empty");
        }
        if(amount == null || amount.signum() < 0){
            throw  new IllegalArgumentException("amount cannot be negative or null");
        }
    }
}
