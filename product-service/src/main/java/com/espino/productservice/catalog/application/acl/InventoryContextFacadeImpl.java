package com.espino.productservice.catalog.application.acl;

import com.espino.productservice.catalog.domain.exceptions.ProductNotFoundException;
import com.espino.productservice.catalog.domain.model.queries.GetProductStatusById;
import com.espino.productservice.catalog.domain.model.valueobjects.ProductStatus;
import com.espino.productservice.catalog.domain.services.ProductQueryService;
import com.espino.productservice.catalog.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {
    private final ProductQueryService productQueryService;

    public InventoryContextFacadeImpl(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @Override
    public ProductStatus getProductStatusById(Long id) {
        var query = new GetProductStatusById(id);

        return productQueryService.handle(query).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
