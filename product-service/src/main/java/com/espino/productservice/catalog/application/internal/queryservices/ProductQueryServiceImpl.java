package com.espino.productservice.catalog.application.internal.queryservices;

import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.queries.GetProductByTextQuery;
import com.espino.productservice.catalog.domain.model.queries.GetProductStatusById;
import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;
import com.espino.productservice.catalog.domain.services.ProductQueryService;
import com.espino.productservice.catalog.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> handle(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> handle(GetProductByTextQuery query, Pageable pageable) {
        return this.productRepository.findByNameStartingWithIgnoreCaseOrBrandStartingWithIgnoreCase(
                query.text(),
                query.text(),
                pageable
        );
    }

    @Override
    public Optional<ProductStatus> handle(GetProductStatusById query) {
        return this.productRepository.findProductStatusById(query.id());
    }


}
