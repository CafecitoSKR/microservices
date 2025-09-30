package com.espino.orderservice.orders.interfaces.rest;

import com.espino.orderservice.orders.domain.services.OrderCommandService;
import com.espino.orderservice.orders.domain.services.OrderItemCommandService;
import com.espino.orderservice.orders.interfaces.rest.resources.CreateOrderItemResource;
import com.espino.orderservice.orders.interfaces.rest.resources.CreateOrderResource;
import com.espino.orderservice.orders.interfaces.rest.resources.OrderItemResource;
import com.espino.orderservice.orders.interfaces.rest.resources.OrderResource;
import com.espino.orderservice.orders.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.espino.orderservice.orders.interfaces.rest.transform.CreateOrderItemCommandFromResourceAssembler;
import com.espino.orderservice.orders.interfaces.rest.transform.OrderItemResourceFromEntityAssembler;
import com.espino.orderservice.orders.interfaces.rest.transform.OrderResourceFromEntityAssembler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/orders",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Operation related to orders")
@AllArgsConstructor
public class OrderController {
    private final OrderCommandService orderCommandService;
    private final OrderItemCommandService orderItemCommandService;

    @Operation(summary = "Create a Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Order Created"),
            @ApiResponse(responseCode = "400",description = "Invalid input"),
    })
    @PostMapping()
    public ResponseEntity<OrderResource> createProduct(@RequestBody CreateOrderResource resource) {
        var order = orderCommandService.handle(CreateOrderCommandFromResourceAssembler.fromResource(resource));
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var orderCreated = order.get();
        return new ResponseEntity<>(OrderResourceFromEntityAssembler.fromEntity(orderCreated), CREATED);
    }

    @Operation(summary = "Create a Order Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Order Item Created"),
            @ApiResponse(responseCode = "400",description = "Invalid input"),
    })
    @PostMapping("/order-item")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<OrderItemResource>> createOrderItem(@RequestBody CreateOrderItemResource resource) {
        var orderItem = orderItemCommandService.handle(CreateOrderItemCommandFromResourceAssembler.fromResource(resource));
        if(orderItem.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
        var orderItemCreated = orderItem.get();

        return CompletableFuture.completedFuture(new ResponseEntity<>(OrderItemResourceFromEntityAssembler.fromEntity(orderItemCreated), CREATED));

    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    @GetMapping("/helloWorld")
    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.completedFuture("Hola");
    }

    //TODO: Global Exception Handler
    public CompletableFuture<String> fallbackMethod(CreateOrderItemResource resource, Throwable throwable) {

        return CompletableFuture.supplyAsync(()-> "Oops! Something went wrong, please order after some time!");
    }



}
