package com.espino.inventoryservice.inventory.infrastructure.persistence.jpa.repositories;

import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(Long productId);
}
