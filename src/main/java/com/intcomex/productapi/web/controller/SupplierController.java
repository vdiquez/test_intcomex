package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.web.dto.SupplierRequestDto;
import com.intcomex.productapi.web.dto.SupplierResponseDto;
import com.intcomex.productapi.web.mapper.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    @GetMapping
    public List<SupplierResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public SupplierResponseDto create(@RequestBody SupplierRequestDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
