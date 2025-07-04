package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "ProductController")
public class ProductController {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Operation(summary = "get all products")
    @GetMapping
    public List<ProductResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "post create product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
