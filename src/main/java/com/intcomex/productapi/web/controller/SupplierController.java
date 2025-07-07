package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.application.service.SupplierService;
import com.intcomex.productapi.web.dto.SupplierRequestDto;
import com.intcomex.productapi.web.dto.SupplierResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "SupplierController")
@ConditionalOnProperty(name = "endpoints.supplier.enabled", havingValue = "true")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Get all suppliers")
    @GetMapping
    public List<SupplierResponseDto> getAll() {
        return supplierService.findAll();
    }

    @Operation(summary = "Post create supplier")
    @PostMapping
    public SupplierResponseDto create(@RequestBody SupplierRequestDto dto) {
        return supplierService.save(dto);
    }
}
