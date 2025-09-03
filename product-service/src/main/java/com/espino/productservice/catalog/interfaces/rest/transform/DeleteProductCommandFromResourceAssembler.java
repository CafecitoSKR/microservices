package com.espino.productservice.catalog.interfaces.rest.transform;


import com.espino.productservice.catalog.domain.model.commands.DeleteProductCommand;

public class DeleteProductCommandFromResourceAssembler {
    public static DeleteProductCommand toCommandFromResource(Long id) {
        return new DeleteProductCommand(
                id
        );
    }
}
