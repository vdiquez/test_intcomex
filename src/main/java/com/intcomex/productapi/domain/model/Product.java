package com.intcomex.productapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String quantityPerUnit;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;

    private Boolean discontinued;
}
