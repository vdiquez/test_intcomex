package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private String categoryName;
    private String supplierName;
    private Double unitPrice;
    private Integer unitsInStock;
    private Boolean discontinued;
}
