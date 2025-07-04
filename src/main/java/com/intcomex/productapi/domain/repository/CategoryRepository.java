package com.intcomex.productapi.domain.repository;

import com.intcomex.productapi.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
