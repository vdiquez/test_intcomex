package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.application.service.CategoryService;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "CategoryController")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "Post create category")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDto create(@ModelAttribute CategoryRequestDto dto) {
        return categoryService.save(dto);
    }
}
