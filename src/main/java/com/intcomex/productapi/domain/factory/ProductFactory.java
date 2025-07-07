package com.intcomex.productapi.domain.factory;

import com.intcomex.productapi.domain.model.Category;
import com.intcomex.productapi.domain.model.Product;
import com.intcomex.productapi.domain.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final Random random = ThreadLocalRandom.current();

    public Product create(List<Category> categories, List<Supplier> suppliers) {
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

