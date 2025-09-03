package com.espino.inventoryservice.inventory.interfaces;

import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;
import com.espino.inventoryservice.inventory.domain.aggregates.queries.GetQuantityByProductIdQuery;
import com.espino.inventoryservice.inventory.domain.services.StockCommandService;
import com.espino.inventoryservice.inventory.domain.services.StockQueryService;
import com.espino.inventoryservice.inventory.interfaces.resources.CreateStockResource;
import com.espino.inventoryservice.inventory.interfaces.resources.StockResource;
import com.espino.inventoryservice.inventory.interfaces.transform.CreateStockCommandFromResourceAssembler;
import com.espino.inventoryservice.inventory.interfaces.transform.StockResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/stocks",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Stocks", description = "Operation related to stock")
@AllArgsConstructor
public class StockController {
    private final StockCommandService stockCommandService;
    private final StockQueryService stockQueryService;

    @Operation(summary = "Create a Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Stock Created"),
            @ApiResponse(responseCode = "400",description = "Invalid input"),
    })
    @PostMapping
    public ResponseEntity<StockResource> createProduct(@RequestBody CreateStockResource resource) {
        Optional<Stock> stock = stockCommandService
                .handle(CreateStockCommandFromResourceAssembler.fromResource(resource));

        if(stock.isEmpty()) {
            throw new IllegalArgumentException("Stock creation failed");
        }
        var stockCreated = stock.get();

        return new ResponseEntity<>(StockResourceFromEntityAssembler.fromEntity(stockCreated),CREATED);
    }
    @Operation(summary = "Get available Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Stock Created"),
            @ApiResponse(responseCode = "400",description = "Invalid input"),
    })
    @GetMapping("/available-quantity/{productId}")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable Long productId) {
        var query = new GetQuantityByProductIdQuery(productId);
        var availableStock = stockQueryService.handle(query);
        return new ResponseEntity<>(availableStock,OK);
    }

}
