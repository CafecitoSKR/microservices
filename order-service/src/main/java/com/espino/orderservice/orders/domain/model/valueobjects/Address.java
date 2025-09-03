package com.espino.orderservice.orders.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
        String street,
        String number,
        String city,
        String postalCode,
        String country
) {
    public Address(){
        this(null,null,null,null,null);
    }
    public String getAddress() {
        return "%s %s, %s, %s, %s".formatted(street, number, city, postalCode, country);
    }
    public Address{
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street must not be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be null or blank");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code must not be null or blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country must not be null or blank");
        }
    }
}
