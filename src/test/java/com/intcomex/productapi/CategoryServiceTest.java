package com.intcomex.productapi;

import com.intcomex.productapi.application.service.CategoryService;
import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    public CategoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Category> categories = List.of(new Category());
        when(repository.findAll()).thenReturn(categories);
        when(mapper.toDto(any())).thenReturn(new CategoryResponseDto());

        List<CategoryResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        CategoryRequestDto dto = new CategoryRequestDto();
        Category category = new Category();
        when(mapper.toEntity(dto)).thenReturn(category);
        when(repository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(new CategoryResponseDto());

        CategoryResponseDto result = service.save(dto);

        assertNotNull(result);
        verify(repository).save(category);
    }
}
