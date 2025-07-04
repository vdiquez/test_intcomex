package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Shipper;
import com.intcomex.productapi.web.dto.ShipperRequestDto;
import com.intcomex.productapi.web.dto.ShipperResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipperMapper {
    Shipper toEntity(ShipperRequestDto dto);
    ShipperResponseDto toDto(Shipper entity);
}
