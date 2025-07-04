package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto save(ProductRequestDto dto) {
        Product entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }
}
