package com.intcomex.productapi.integration;

import com.intcomex.productapi.application.service.ProductService;
import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.domain.factory.ProductFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
    private SupplierRepository supplierRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductFactory productFactory;

    @Test
    void generateAndSaveProducts_shouldPersistAllProducts() {
        // Arrange: preparamos categorías y proveedores
        Category category = Category.builder()
                .categoryName("Electrónica")
                .description("Tecnología y gadgets")
                .picture(null)
                .build();
        Supplier supplier = Supplier.builder()
                .companyName("TechSupplier")
                .contactName("John Doe")
                .build();

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
