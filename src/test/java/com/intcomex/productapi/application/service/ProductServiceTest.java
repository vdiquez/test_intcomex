package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.factory.ProductFactory;
import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.infrastructure.specification.ProductSpecification;
import com.intcomex.productapi.web.dto.*;
import com.intcomex.productapi.web.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductFactory productFactory;
    @Mock private ProductMapper productMapper;

    @Mock private ProductService self; // mock del bean proxificado por @Lazy

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Product> products = List.of(new Product());
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDto(any())).thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = productService.findAll();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        ProductRequestDto dto = new ProductRequestDto();
        Product product = new Product();
        when(productMapper.toEntity(dto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(new ProductResponseDto());

        ProductResponseDto result = productService.save(dto);

        assertNotNull(result);
        verify(productRepository).save(product);
    }

    @Test
    void triggerAsyncGeneration_shouldDelegateToSelf() {
        // Arrange
        int quantity = 5;

        // Inject manualmente el mock de self
        productService = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);
        setField(productService, "self", self); // Usamos reflection

        // Act
        productService.triggerAsyncGeneration(quantity);

        // Assert
        verify(self, times(1)).generateAndSaveProducts(quantity);
    }

    // Método auxiliar para inyectar el mock de self con reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = ProductService.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateAndSaveProducts_shouldPersistBatchesCorrectly() {
        // Arrange
        int quantity = 3; // menor a BATCH_SIZE para evitar complicaciones

        List<Category> categories = List.of(new Category());
        List<Supplier> suppliers = List.of(new Supplier());
        Product fakeProduct = new Product();

        when(categoryRepository.findAll()).thenReturn(categories);
        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(productFactory.create(categories, suppliers)).thenReturn(fakeProduct);

        // Mock del EntityManager
        EntityManager entityManager = mock(EntityManager.class);
        ProductService service = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);
        setField(service, "entityManager", entityManager);

        // Act
        service.generateAndSaveProducts(quantity);

        // Assert
        verify(categoryRepository).findAll();
        verify(supplierRepository).findAll();
        verify(productFactory, times(quantity)).create(categories, suppliers);
        verify(entityManager, times(quantity)).persist(fakeProduct);
        verify(entityManager, times(1)).flush();
        verify(entityManager, times(1)).clear();
    }

    @Test
    void searchProducts_shouldReturnPagedResponse() {
        // Arrange
        ProductSearchRequestDto request = new ProductSearchRequestDto();
        Specification<Product> mockSpec = mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        ProductResponseDto responseDto = new ProductResponseDto();

        List<Product> productList = List.of(product);
        Page<Product> productPage = new PageImpl<>(productList, pageable, 1);

        // Mocks estáticos o indirectos
        try (MockedStatic<ProductSpecification> mockedSpec = mockStatic(ProductSpecification.class)) {
            mockedSpec.when(() -> ProductSpecification.build(request)).thenReturn(mockSpec);

            when(((JpaSpecificationExecutor<Product>) productRepository)
                    .findAll(any(Specification.class), any(Pageable.class)))
                    .thenReturn(productPage);

            when(productMapper.toDto(product)).thenReturn(responseDto);

            // Testeamos usando instancia real con dependencias mockeadas
            ProductService service = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);

            // Act
            PagedResponseDto<ProductResponseDto> result = service.searchProducts(request);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getContent().size());
            assertEquals(responseDto, result.getContent().get(0));
            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getTotalPages());
            assertEquals(0, result.getPage());
            assertEquals(10, result.getSize());

            verify((JpaSpecificationExecutor<Product>) productRepository)
                    .findAll(any(Specification.class), any(Pageable.class));
            verify(productMapper).toDto(product);
        }
    }

    @Test
    void getProductDetail_shouldReturnDetailDto() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        ProductDetailResponseDto expectedDto = new ProductDetailResponseDto();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDetailDto(product)).thenReturn(expectedDto);

        ProductService service = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);

        // Act
        ProductDetailResponseDto result = service.getProductDetail(productId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(productRepository).findById(productId);
        verify(productMapper).toDetailDto(product);
    }

    @Test
    void getProductDetail_shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductService service = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            service.getProductDetail(productId);
        });

        assertEquals("Producto no encontrado con ID: " + productId, thrown.getMessage());
        verify(productRepository).findById(productId);
        verifyNoInteractions(productMapper);
    }

    @Test
    void resolvePageable_shouldHandleNonNullValues() {
        ProductSearchRequestDto request = new ProductSearchRequestDto();
        request.setPage(2);
        request.setSize(15);

        // Llamar al método indirectamente
        Specification<Product> spec = mock(Specification.class);
        Page<Product> page = new PageImpl<>(
                List.of(new Product()),
                PageRequest.of(2, 15, Sort.by("productName").ascending()),
                30
        );

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(productMapper.toDto(any())).thenReturn(new ProductResponseDto());

        try (MockedStatic<ProductSpecification> mockedSpec = mockStatic(ProductSpecification.class)) {
            mockedSpec.when(() -> ProductSpecification.build(request)).thenReturn(spec);

            ProductService service = new ProductService(productRepository, productMapper, supplierRepository, categoryRepository, productFactory);

            PagedResponseDto<ProductResponseDto> result = service.searchProducts(request);

            assertEquals(2, result.getPage());
            assertEquals(15, result.getSize());
        }
    }


}
