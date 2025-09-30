package com.espino.inventoryservice.inventory.domain.aggregates.model;

import com.espino.inventoryservice.inventory.domain.aggregates.entities.ReservationItem;
import com.espino.inventoryservice.inventory.domain.aggregates.valueobjects.ReservationStatus;
import com.espino.inventoryservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class StockReservation extends AuditableAbstractAggregateRoot<StockReservation> {

    @NotNull
    private UUID reservationId;

    @NotNull
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(mappedBy = "stockReservation",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY)
    private List<ReservationItem> reservationItems;

    protected StockReservation() {}


//    public StockReservation(CreateReservationCommand command) {
//        this.reservationId = UUID.randomUUID();
//        this.orderId= command.orderId();
//        this.status=ReservationStatus.PENDING;
//        this.reservationItems = command.items()
//                .stream()
//                .map(
//                        reservationItemDraft ->
//                                new ReservationItem(reservationItemDraft.productId(),reservationItemDraft.quantity())).toList();
//    }
    public StockReservation(Long orderId){
        this.orderId = orderId;
        this.status = ReservationStatus.PENDING;
        this.reservationId = UUID.randomUUID();
    }

    public StockReservation create(Long orderId){
        return new StockReservation(orderId);
    }
    public void addItem(ReservationItem item){
        this.reservationItems.add(item);
        item.setStockReservation(this);
    }
}
