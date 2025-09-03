package com.espino.productservice.catalog.infrastructure.persistence.jpa.repositories;

import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameStartingWithIgnoreCaseOrBrandStartingWithIgnoreCase(String name, String brand, Pageable pageable);
    Optional<ProductStatus> findProductStatusById(Long id);
}
