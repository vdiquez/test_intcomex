package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponseDto {
    private Long supplierId;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String city;
    private String country;
    private String phone;
}
