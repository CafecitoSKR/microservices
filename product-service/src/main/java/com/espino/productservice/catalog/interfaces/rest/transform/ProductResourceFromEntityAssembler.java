package com.espino.productservice.catalog.interfaces.rest.transform;


import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.interfaces.rest.resources.ProductResource;

public class ProductResourceFromEntityAssembler {
    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getBrand(),
                entity.getQuantity(),
                entity.getSku().asString(),
                entity.getStatus()
        );
    }
}
