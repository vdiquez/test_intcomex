package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.factory.ProductFactory;
import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.CategoryRepository;
import com.intcomex.productapi.domain.repository.ProductRepository;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.web.dto.ProductRequestDto;
import com.intcomex.productapi.web.dto.ProductResponseDto;
import com.intcomex.productapi.web.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int BATCH_SIZE = 1000;

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFactory productFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Lazy
    @Autowired
    private ProductService self;

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public ProductResponseDto save(ProductRequestDto dto) {
        Product entity = mapper.toEntity(dto);
        return mapper.toDto(productRepository.save(entity));
    }

    public void triggerAsyncGeneration(int quantity) {
        self.generateAndSaveProducts(quantity);
    }

    @Async
    @Transactional
    public void generateAndSaveProducts(int quantity) {
        List<Category> categories = categoryRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();

        for (int i = 0; i < quantity; i += BATCH_SIZE) {
            List<Product> batch = IntStream.range(0, Math.min(BATCH_SIZE, quantity - i))
                    .mapToObj(n -> productFactory.create(categories, suppliers))
                    .toList();

            batch.forEach(entityManager::persist);
            entityManager.flush();
            entityManager.clear();
        }
    }

    private Product createRandomProduct(List<Category> categories, List<Supplier> suppliers) {
        Random random = ThreadLocalRandom.current();

        Category randomCategory = categories.get(random.nextInt(categories.size()));
        Supplier randomSupplier = suppliers.get(random.nextInt(suppliers.size()));

        return Product.builder()
                .productName("Producto " + UUID.randomUUID().toString().substring(0, 8))
                .unitPrice(BigDecimal.valueOf(random.nextDouble(10, 500)))
                .unitsInStock(random.nextInt(1, 100))
                .discontinued(false)
                .category(randomCategory)
                .supplier(randomSupplier)
                .build();
    }
}
