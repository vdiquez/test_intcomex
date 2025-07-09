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
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public PagedResponseDto<ProductResponseDto> searchProducts(ProductSearchRequestDto request) {
        Specification<Product> spec = ProductSpecification.build(request);

        Pageable pageable = resolvePageable(request);
        Page<Product> pageResult = productRepository.findAll(spec, pageable);

        List<ProductResponseDto> dtos = pageResult.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        return PagedResponseDto.<ProductResponseDto>builder()
                .content(dtos)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    public ProductDetailResponseDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + productId));

        return mapper.toDetailDto(product);
    }

    private Pageable resolvePageable(ProductSearchRequestDto request) {
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        return PageRequest.of(page, size, Sort.by("productName").ascending());
    }
}
