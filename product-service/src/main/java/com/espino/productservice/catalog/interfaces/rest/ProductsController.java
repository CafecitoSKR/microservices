package com.espino.productservice.catalog.interfaces.rest;

import com.espino.productservice.catalog.domain.model.aggregates.Product;
import com.espino.productservice.catalog.domain.model.commands.DeleteProductCommand;
import com.espino.productservice.catalog.domain.model.queries.GetProductByTextQuery;
import com.espino.productservice.catalog.domain.services.ProductCommandService;
import com.espino.productservice.catalog.domain.services.ProductQueryService;
import com.espino.productservice.catalog.interfaces.rest.resources.CreateProductResource;
import com.espino.productservice.catalog.interfaces.rest.resources.DeleteProductResource;
import com.espino.productservice.catalog.interfaces.rest.resources.ProductResource;
import com.espino.productservice.catalog.interfaces.rest.resources.UpdateProductResource;
import com.espino.productservice.catalog.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.espino.productservice.catalog.interfaces.rest.transform.DeleteProductCommandFromResourceAssembler;
import com.espino.productservice.catalog.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.espino.productservice.catalog.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/products",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Operation related to products")
public class ProductsController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public ProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService, DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;

        this.discoveryClient = discoveryClient;
        restClient = RestClient.builder().build();
    }

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    @PostMapping
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource resource) {
        Optional<Product> product = productCommandService
                .handle(CreateProductCommandFromResourceAssembler.toCommandFromResource(resource));
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product creation failed");
        }
        var productCreated = product.get();

        return new ResponseEntity<>(ProductResourceFromEntityAssembler.toResourceFromEntity(productCreated), CREATED);
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long id, @RequestBody UpdateProductResource resource) {

        var product = productCommandService.handle(UpdateProductCommandFromResourceAssembler.toCommandFromResource(resource, id));
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product update failed");
        }
        var productUpdated = product.get();

        return new ResponseEntity<>(ProductResourceFromEntityAssembler.toResourceFromEntity(productUpdated), CREATED);

    }

    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products Founded"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ProductResource>> getProducts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> products = productQueryService.handle(pageable);

        Page<ProductResource> productResources = products.map(ProductResourceFromEntityAssembler::toResourceFromEntity);
        return new ResponseEntity<>(productResources, CREATED);
    }

    @Operation(summary = "Search Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products Founded"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/search/{text}")
    public ResponseEntity<Page<ProductResource>> searchProducts(
            @PathVariable String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productQueryService.handle(new GetProductByTextQuery(text), pageable);

        Page<ProductResource> productResources = products.map(ProductResourceFromEntityAssembler::toResourceFromEntity);

        return new ResponseEntity<>(productResources, CREATED);
    }

    @Operation(summary = "Delete products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResource> deleteProduct(@PathVariable Long id) {
        DeleteProductCommand command = DeleteProductCommandFromResourceAssembler.toCommandFromResource(id);
        productCommandService.handle(command);
        DeleteProductResource response = new DeleteProductResource(id, "Product Deleted Successfully");
        return new ResponseEntity<>(response, ACCEPTED);
    }


    @GetMapping("helloEureka")
    public String helloWorld() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service").get(0);
        String orderServiceResponse = restClient.get()
                .uri(serviceInstance.getUri() + "/helloWorld")
                .retrieve()
                .body(String.class);
        return orderServiceResponse;
    }
}

