package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "CategoryController")
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Operation(summary = "get all categories")
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "post create category")
    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
