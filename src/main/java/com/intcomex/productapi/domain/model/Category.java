package com.intcomex.productapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true, nullable = false)
    private String categoryName;

    private String description;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
