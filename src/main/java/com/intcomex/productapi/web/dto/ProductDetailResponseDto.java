package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {

    private ProductResponseDto summary;

    private String categoryDescription;
    private String categoryImageBase64;

}
