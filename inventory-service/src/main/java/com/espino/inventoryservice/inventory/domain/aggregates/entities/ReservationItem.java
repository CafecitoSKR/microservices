package com.espino.inventoryservice.inventory.domain.aggregates.entities;

import com.espino.inventoryservice.inventory.domain.aggregates.model.StockReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReservationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_reservation_id")
    private StockReservation stockReservation;

    private int quantity;
    protected ReservationItem() {}


    public ReservationItem(Long productId, int quantity, StockReservation stockReservation) {
        this.productId = productId;
        this.quantity = quantity;
        this.stockReservation = stockReservation;
    }
}
