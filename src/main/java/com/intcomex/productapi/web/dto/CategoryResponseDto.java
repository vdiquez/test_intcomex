package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private String description;
}
