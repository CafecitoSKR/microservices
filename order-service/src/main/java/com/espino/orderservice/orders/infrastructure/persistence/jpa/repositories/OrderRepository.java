package com.espino.orderservice.orders.infrastructure.persistence.jpa.repositories;

import com.espino.orderservice.orders.domain.model.aggregates.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
