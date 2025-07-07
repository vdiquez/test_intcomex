package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.infrastructure.utils.ImageUtils;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {ImageUtils.class})
public interface CategoryMapper {
    @Mapping(target = "picture", expression = "java(ImageUtils.toByteArray(dto.getPicture()))")
    Category toEntity(CategoryRequestDto dto);
    CategoryResponseDto toDto(Category entity);
}
