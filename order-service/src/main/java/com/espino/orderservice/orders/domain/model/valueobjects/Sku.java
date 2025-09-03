package com.espino.orderservice.orders.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public record Sku(@Column(name = "sku", length = 40, nullable = false) String value) {
    private static final Pattern VALID = Pattern.compile("^[A-Z0-9-]{4,40}$");

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Sku of(String raw){
        if(raw==null || raw.isEmpty()){
            throw new IllegalArgumentException("Cannot create a Sku from a null or empty string");
        }
        String value = raw.trim().toUpperCase();
        if(!VALID.matcher(value).matches()){
            throw new IllegalArgumentException("Invalid SKU format (A-Z, 0-9, '-', length 4..40)");
        }
        return new Sku(value);
    }

    public Sku {
        Objects.requireNonNull(value, "SKU is required");
    }

    @JsonValue
    public String asString() { return value; }
}
