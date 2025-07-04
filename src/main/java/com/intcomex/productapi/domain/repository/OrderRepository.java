package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
