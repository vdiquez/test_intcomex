package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDto dto);
    CategoryResponseDto toDto(Category entity);
}
