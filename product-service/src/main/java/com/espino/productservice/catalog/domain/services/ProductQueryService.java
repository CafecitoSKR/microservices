package com.espino.productservice.catalog.domain.services;

import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.queries.GetProductByTextQuery;
import com.espino.productservice.catalog.domain.model.queries.GetProductStatusById;
import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductQueryService {
    Page<Product> handle(Pageable pageable);
    Page<Product> handle(GetProductByTextQuery query, Pageable pageable);
    Optional<ProductStatus> handle(GetProductStatusById query);
}
