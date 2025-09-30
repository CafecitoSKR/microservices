package com.espino.inventoryservice.inventory.infrastructure.persistence.jpa.repositories;

import com.espino.inventoryservice.inventory.domain.aggregates.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {
}
