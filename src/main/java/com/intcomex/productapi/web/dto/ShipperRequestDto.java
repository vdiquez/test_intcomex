package com.intcomex.productapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipperRequestDto {
    @NotBlank
    private String companyName;
    private String phone;
}
