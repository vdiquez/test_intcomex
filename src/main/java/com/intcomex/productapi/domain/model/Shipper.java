package com.intcomex.productapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shippers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipperId;

    @Column(nullable = false)
    private String companyName;

    private String phone;
}
