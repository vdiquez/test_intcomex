package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.application.service.ProductService;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.*;
import com.intcomex.productapi.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public ResponseEntity<PagedResponseDto<ProductResponseDto>> searchProducts(
            @ParameterObject @ModelAttribute ProductSearchRequestDto request
    ) {
        var response = productService.searchProducts(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener el detalle de un producto por su ID")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetail(
            @Parameter(description = "ID del producto") @PathVariable("id") Long id) {
        ProductDetailResponseDto detail = productService.getProductDetail(id);
        return ResponseEntity.ok(detail);
    }


    @Operation(summary = "post create products")
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> create(@RequestParam("quantity") int quantity) {
        productService.triggerAsyncGeneration(quantity);
        return ResponseEntity.accepted().build();
    }
}
