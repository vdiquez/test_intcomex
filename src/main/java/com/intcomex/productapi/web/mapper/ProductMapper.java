package com.intcomex.productapi.web.mapper;

import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.web.dto.ProductDetailResponseDto;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto dto);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "supplier.companyName", target = "supplierName")
    ProductResponseDto toDto(Product product);

    @Named("toDetailDto")
    default ProductDetailResponseDto toDetailDto(Product product) {
        ProductResponseDto summary = toDto(product);

        String base64Image = encodeImage(product.getCategory().getPicture());

        return ProductDetailResponseDto.builder()
                .summary(summary)
                .categoryDescription(product.getCategory().getDescription())
                .categoryImageBase64(base64Image)
                .build();
    }

    default String encodeImage(byte[] image) {
        return image != null ? Base64.getEncoder().encodeToString(image) : null;
    }

}
