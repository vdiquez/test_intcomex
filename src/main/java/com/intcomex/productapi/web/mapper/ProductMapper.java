package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto dto);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "supplier.companyName", target = "supplierName")
    ProductResponseDto toDto(Product product);
}
