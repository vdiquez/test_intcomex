package com.intcomex.productapi.infrastructure.specification;

import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.web.dto.ProductSearchRequestDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> build(ProductSearchRequestDto request) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getProductName() != null) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
            }

            if (request.getCategoryName() != null) {
                Join<Object, Object> categoryJoin = root.join("category", JoinType.INNER);
                predicates.add(cb.like(cb.lower(categoryJoin.get("categoryName")), "%" + request.getCategoryName().toLowerCase() + "%"));
            }

            if (request.getSupplierName() != null) {
                Join<Object, Object> supplierJoin = root.join("supplier", JoinType.INNER);
                predicates.add(cb.like(cb.lower(supplierJoin.get("companyName")), "%" + request.getSupplierName().toLowerCase() + "%"));
            }

            if (request.getDiscontinued() != null) {
                predicates.add(cb.equal(root.get("discontinued"), request.getDiscontinued()));
            }

            if (request.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("unitPrice"), BigDecimal.valueOf(request.getMinPrice())));
            }

            if (request.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("unitPrice"), BigDecimal.valueOf(request.getMaxPrice())));
            }

            if (request.getMinStock() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("unitsInStock"), request.getMinStock()));
            }

            if (request.getMaxStock() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("unitsInStock"), request.getMaxStock()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
