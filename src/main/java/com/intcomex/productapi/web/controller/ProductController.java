package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
