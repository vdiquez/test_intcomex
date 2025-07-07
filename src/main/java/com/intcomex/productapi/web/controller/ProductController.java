package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.application.service.ProductService;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "ProductController")
public class ProductController {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductService productService;

    @Operation(summary = "get all products")
    @GetMapping("/products")
    public List<ProductResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "get product detail")
    @GetMapping("/products/{id}")
    public List<ProductResponseDto> getDetail() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "post create products")
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> create(@RequestParam("quantity") int quantity) {
        productService.triggerAsyncGeneration(quantity);
        return ResponseEntity.accepted().build();
    }
}
