package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
