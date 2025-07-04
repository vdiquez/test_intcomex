package com.intcomex.productapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

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

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Detalles de producto
    private String quantityPerUnit;

    @Column(precision = 10, scale = 2)
    private Double unitPrice;

    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;

    private Boolean discontinued;
}
