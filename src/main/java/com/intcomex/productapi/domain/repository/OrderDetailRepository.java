package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, com.intcomex.productapi.domain.model.OrderDetailPK> {
}
