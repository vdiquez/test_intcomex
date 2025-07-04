package com.intcomex.productapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank
    private String categoryName;
    private String description;
    private byte[] picture;
}
