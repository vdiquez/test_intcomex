package com.intcomex.productapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;

    private LocalDate birthDate;
    private LocalDate hireDate;

    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String extension;

    @Lob
    private byte[] photo;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "reports_to")
    private Employee reportsTo;

    @OneToMany(mappedBy = "reportsTo")
    private List<Employee> subordinates;
}
