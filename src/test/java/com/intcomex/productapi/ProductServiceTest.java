package com.intcomex.productapi;

import com.intcomex.productapi.application.service.ProductService;
import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Product> products = List.of(new Product());
        when(repository.findAll()).thenReturn(products);
        when(mapper.toDto(any())).thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        ProductRequestDto dto = new ProductRequestDto();
        Product product = new Product();
        when(mapper.toEntity(dto)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(new ProductResponseDto());

        ProductResponseDto result = service.save(dto);

        assertNotNull(result);
        verify(repository).save(product);
    }
}
