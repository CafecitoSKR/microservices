package com.espino.productservice.catalog.application.internal.commandservices;

import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.commands.CreateProductCommand;
import com.espino.productservice.catalog.domain.model.commands.DeleteProductCommand;
import com.espino.productservice.catalog.domain.model.commands.UpdateProductCommand;
import com.espino.productservice.catalog.domain.services.ProductCommandService;
import com.espino.productservice.catalog.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository productRepository;
    private final WebClient webClient;

    public ProductCommandServiceImpl(ProductRepository productRepository, WebClient webClient) {
        this.productRepository = productRepository;
        this.webClient = webClient;
    }

    @Override
    public Optional<Product> handle(CreateProductCommand command) {
        var product = new Product(command);
        var productCreated= productRepository.save(product);
        return Optional.of(productCreated);
    }

    @Override
    public Optional<Product> handle(UpdateProductCommand command) {
        var product = productRepository.findById(command.productId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        product.update(command);

        var productUpdated= productRepository.save(product);

        return Optional.of(productUpdated);
    }

    @Override
    public void handle(DeleteProductCommand command) {
        var product = productRepository.findById(command.productId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }
}
