package com.espino.orderservice.orders.infrastructure.http.inventory;

import com.espino.orderservice.orders.application.internal.outboundservices.inventory.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final WebClient.Builder webClientBuilder;
    private final String uri = "http://INVENTORY-SERVICE/api/v1/stocks";

    @Override
    public int getAvailableStockQuantity(Long productId) {
        var availableQuantity = webClientBuilder.build().get()
                .uri(uri + "/available-quantity/{productId}", productId)
                .retrieve()
                .bodyToMono(Integer.class).block();
        if(availableQuantity == null) {
            throw new IllegalArgumentException("Product Stock not found");
        }
        return availableQuantity;
    }

}
