package com.espino.inventoryservice.inventory.interfaces;

import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;
import com.espino.inventoryservice.inventory.domain.aggregates.queries.GetQuantityByProductIdQuery;
import com.espino.inventoryservice.inventory.domain.services.StockCommandService;
import com.espino.inventoryservice.inventory.domain.services.StockQueryService;
import com.espino.inventoryservice.inventory.domain.services.StockReservationCommandService;
import com.espino.inventoryservice.inventory.interfaces.resources.CreateReservationResource;
import com.espino.inventoryservice.inventory.interfaces.resources.CreateStockResource;
import com.espino.inventoryservice.inventory.interfaces.resources.StockReservationResource;
import com.espino.inventoryservice.inventory.interfaces.resources.StockResource;
import com.espino.inventoryservice.inventory.interfaces.transform.CreateReservationCommandFromResourceAssembler;
import com.espino.inventoryservice.inventory.interfaces.transform.CreateStockCommandFromResourceAssembler;
import com.espino.inventoryservice.inventory.interfaces.transform.StockReservationResourceFromEntityAssembler;
import com.espino.inventoryservice.inventory.interfaces.transform.StockResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/stocks",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Stocks", description = "Operation related to stock")

public class StockController {
    private final StockCommandService stockCommandService;
    private final StockQueryService stockQueryService;
    private final StockReservationCommandService stockReservationCommandService;

    public StockController(StockCommandService stockCommandService, StockQueryService stockQueryService, StockReservationCommandService stockReservationCommandService) {
        this.stockCommandService = stockCommandService;
        this.stockQueryService = stockQueryService;
        this.stockReservationCommandService = stockReservationCommandService;
    }

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
    @Operation(summary = "Create a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Reservation Created"),
            @ApiResponse(responseCode = "400", description = "Invalid Input")
    })
    @PostMapping("/reservations")
    public ResponseEntity<StockReservationResource> createStockReservation(@RequestBody CreateReservationResource resource){
        var command = CreateReservationCommandFromResourceAssembler.toCommand(resource);

        var reservationOptional = stockReservationCommandService.handle(command);

        if(reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Reservation creation failed");
        }
        var reservationCreated = reservationOptional.get();

        return new ResponseEntity<>(StockReservationResourceFromEntityAssembler.toResource(reservationCreated),CREATED);


    }

}
