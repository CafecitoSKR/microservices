package com.espino.productservice.catalog.interfaces.acl;


import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;

public interface InventoryContextFacade {
    ProductStatus getProductStatusById(Long id);
}
