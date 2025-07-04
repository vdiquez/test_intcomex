package com.intcomex.productapi.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String title;
    private String city;
    private String country;
}
