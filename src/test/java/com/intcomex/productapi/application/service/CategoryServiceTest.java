package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.dto.CategoryResponseDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testFindAll() {
        // Arrange
        List<Category> categories = List.of(new Category());
        when(repository.findAll()).thenReturn(categories);
        when(mapper.toDto(any())).thenReturn(new CategoryResponseDto());

        // Act
        List<CategoryResponseDto> result = service.findAll();

        // Assert
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        MockMultipartFile picture = new MockMultipartFile(
                "picture",
                "test-image.png",
                "image/png",
                "Fake Image Content".getBytes()
        );

        CategoryRequestDto dto = CategoryRequestDto.builder()
                .categoryName("Cloud")
                .description("Servicios en la nube")
                .picture(picture)
                .build();

        Category categoryMapped = Category.builder()
                .categoryName("Cloud")
                .description("Servicios en la nube")
                .picture("Fake Image Content".getBytes())
                .build();

        CategoryResponseDto expectedResponse = CategoryResponseDto.builder()
                .categoryName("Cloud")
                .description("Servicios en la nube")
                .build();


        when(mapper.toEntity(any(CategoryRequestDto.class))).thenReturn(categoryMapped);
        when(repository.save(any(Category.class))).thenReturn(categoryMapped);
        when(mapper.toDto(categoryMapped)).thenReturn(expectedResponse);

        // Act
        CategoryResponseDto result = service.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Cloud", result.getCategoryName());
        assertArrayEquals("Fake Image Content".getBytes(), categoryMapped.getPicture());
        verify(repository).save(any(Category.class));
    }

}
