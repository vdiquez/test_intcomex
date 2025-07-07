package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.infrastructure.utils.ImageUtils;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto save(CategoryRequestDto dto) {
        Category entity = Category.builder()
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .picture(ImageUtils.toByteArray(dto.getPicture()))
                .build();

        return mapper.toDto(repository.save(entity));
    }

    private byte[] extractBytes(MultipartFile file) {
        try {
            return file != null && !file.isEmpty() ? file.getBytes() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo la imagen", e);
        }
    }
}
