package com.intcomex.productapi.web.dto;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parámetros de búsqueda y paginación para productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequestDto {
    @Schema(description = "Nombre parcial del producto")
    private String productName;

    @Schema(description = "Nombre de la categoría")
    private String categoryName;

    @Schema(description = "Nombre del proveedor")
    private String supplierName;

    private Boolean discontinued;
    private Double minPrice;
    private Double maxPrice;
    private Integer minStock;
    private Integer maxStock;

    @Schema(description = "Número de página (empezando desde 0)")
    private Integer page;

    @Schema(description = "Cantidad de elementos por página")
    private Integer size;
}