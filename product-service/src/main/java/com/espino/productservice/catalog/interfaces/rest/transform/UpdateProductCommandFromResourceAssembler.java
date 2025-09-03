package com.espino.productservice.catalog.interfaces.rest.transform;


import com.espino.productservice.catalog.domain.model.commands.UpdateProductCommand;
import com.espino.productservice.catalog.interfaces.rest.resources.UpdateProductResource;

public class UpdateProductCommandFromResourceAssembler {
    public static UpdateProductCommand toCommandFromResource(UpdateProductResource resource, Long id) {
        return new UpdateProductCommand(
                id,
                resource.name(),
                resource.price(),
                resource.brand(),
                resource.quantity(),
                resource.sku(),
                resource.status().toString()
        );
    }
}
