package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipperResponseDto {
    private Long shipperId;
    private String companyName;
    private String phone;
}
