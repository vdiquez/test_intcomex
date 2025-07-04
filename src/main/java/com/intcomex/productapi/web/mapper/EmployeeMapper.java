package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Employee;
import com.intcomex.productapi.web.dto.EmployeeRequestDto;
import com.intcomex.productapi.web.dto.EmployeeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequestDto dto);
    EmployeeResponseDto toDto(Employee entity);
}
