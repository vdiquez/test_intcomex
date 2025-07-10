package com.intcomex.productapi.integration;

import com.intcomex.productapi.application.service.ProductService;
import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.domain.factory.ProductFactory;
import com.intcomex.productapi.web.dto.CategoryRequestDto;
import com.intcomex.productapi.web.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private DataSource dataSource;

    @Test
    void generateAndSaveProducts_shouldPersistAllProducts() {
        // Arrange: preparamos categorías y proveedores

        CategoryRequestDto dto = CategoryRequestDto.builder()
                .categoryName("Electrónica")
                .description("Tecnología y gadgets")
                .picture(new MockMultipartFile(
                        "file",
                        "imagen.png",
                        "image/png",
                        "Imagen binaria simulada".getBytes(StandardCharsets.UTF_8)
                ))
                .build();

        Category category = categoryMapper.toEntity(dto);

        Supplier supplier = Supplier.builder()
                .companyName("TechSupplier")
                .contactName("John Doe")
                .build();

        System.out.println("Picture class: " + category.getPicture().getClass());

        categoryRepository.save(category);
        supplierRepository.save(supplier);

        int quantity = 15;

        // Act
        productService.generateAndSaveProducts(quantity);

        // Assert
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .until(() -> productRepository.findAll().size() == quantity);
    }

}
