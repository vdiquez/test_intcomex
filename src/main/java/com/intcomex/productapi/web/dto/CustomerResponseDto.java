package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private String customerId;
    private String companyName;
    private String contactName;
    private String country;
    private String phone;
}
