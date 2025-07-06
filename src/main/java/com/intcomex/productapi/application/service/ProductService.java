package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Product;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public ProductResponseDto save(ProductRequestDto dto) {
        Product entity = mapper.toEntity(dto);
        return mapper.toDto(productRepository.save(entity));
    }

    @Async
    @Transactional
    public void generateAndSaveProducts(int quantity) {
        for (int i = 0; i < quantity; i += 1000) {
            List<Product> batch = IntStream.range(0, Math.min(1000, quantity - i))
                    .mapToObj(n -> createRandomProduct())
                    .toList();

            for (Product p : batch) {
                entityManager.persist(p);
            }

            entityManager.flush();
            entityManager.clear();
        }
    }

    private Product createRandomProduct() {
        return Product.builder()
                .productName("Producto " + UUID.randomUUID())
                .unitPrice(BigDecimal.valueOf(
                                ThreadLocalRandom.current().nextDouble(10, 500)))
                .unitsInStock(ThreadLocalRandom.current().nextInt(1, 100))
                .discontinued(false)
                .category(categoryRepository.findById(1L).orElse(null)) // o aleatorio
                .supplier(supplierRepository.findById(1L).orElse(null)) // o aleatorio
                .build();
    }
}
