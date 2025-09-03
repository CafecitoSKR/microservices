package com.espino.productservice.catalog.domain.services;


import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.commands.CreateProductCommand;
import com.espino.productservice.catalog.domain.model.commands.DeleteProductCommand;
import com.espino.productservice.catalog.domain.model.commands.UpdateProductCommand;

import java.util.Optional;

public interface ProductCommandService {
    Optional<Product> handle(CreateProductCommand command);
    Optional<Product> handle(UpdateProductCommand command);
    void handle(DeleteProductCommand command);
}
