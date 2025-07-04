package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.web.dto.SupplierRequestDto;
import com.intcomex.productapi.web.dto.SupplierResponseDto;
import com.intcomex.productapi.web.mapper.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    public List<SupplierResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public SupplierResponseDto save(SupplierRequestDto dto) {
        Supplier entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }
}
