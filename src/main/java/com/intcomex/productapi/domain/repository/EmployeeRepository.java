package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
