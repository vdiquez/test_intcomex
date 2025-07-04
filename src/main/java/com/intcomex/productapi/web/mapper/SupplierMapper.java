package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.web.dto.SupplierRequestDto;
import com.intcomex.productapi.web.dto.SupplierResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierRequestDto dto);
    SupplierResponseDto toDto(Supplier entity);
}
