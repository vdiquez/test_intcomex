package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
