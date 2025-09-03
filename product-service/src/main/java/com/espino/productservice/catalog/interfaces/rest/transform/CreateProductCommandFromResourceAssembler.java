package com.espino.productservice.catalog.interfaces.rest.transform;


import com.espino.productservice.catalog.domain.model.commands.CreateProductCommand;
import com.espino.productservice.catalog.interfaces.rest.resources.CreateProductResource;

public class CreateProductCommandFromResourceAssembler {
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(
                resource.name(),
                resource.price(),
                resource.brand(),
                resource.quantity(),
                resource.sku()
                );
    }
}
