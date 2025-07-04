package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
