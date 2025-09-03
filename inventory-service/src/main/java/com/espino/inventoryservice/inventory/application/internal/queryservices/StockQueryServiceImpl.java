package com.espino.inventoryservice.inventory.application.internal.queryservices;

import com.espino.inventoryservice.inventory.domain.aggregates.queries.GetQuantityByProductIdQuery;
import com.espino.inventoryservice.inventory.domain.services.StockQueryService;
import com.espino.inventoryservice.inventory.infrastructure.persistence.jpa.repositories.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockQueryServiceImpl implements StockQueryService {

    private final StockRepository stockRepository;
    @Override
    public Integer handle(GetQuantityByProductIdQuery query) {
        //TODO: verificar que el producto exista en otro microservicio
        var stock = stockRepository.findByProductId(query.productId());
        if(stock.isEmpty()) {
            throw new IllegalArgumentException("Product Stock not found");
        }

        return stock.get().getOnHand() - stock.get().getReserved();

    }
}
