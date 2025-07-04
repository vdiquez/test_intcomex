package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Customer;
import com.intcomex.productapi.web.dto.CustomerRequestDto;
import com.intcomex.productapi.web.dto.CustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequestDto dto);
    CustomerResponseDto toDto(Customer entity);
}
