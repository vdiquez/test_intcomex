package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {
}
