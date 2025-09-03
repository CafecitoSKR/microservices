package com.espino.orderservice.orders.infrastructure.persistence.jpa.repositories;

import com.espino.orderservice.orders.domain.model.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
