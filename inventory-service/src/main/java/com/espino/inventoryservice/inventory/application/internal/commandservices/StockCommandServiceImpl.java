package com.espino.inventoryservice.inventory.application.internal.commandservices;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateStockCommand;
import com.espino.inventoryservice.inventory.domain.aggregates.model.Stock;
import com.espino.inventoryservice.inventory.domain.services.StockCommandService;
import com.espino.inventoryservice.inventory.infrastructure.persistence.jpa.repositories.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StockCommandServiceImpl implements StockCommandService {
    private final StockRepository stockRepository;

    @Override
    public Optional<Stock> handle(CreateStockCommand command) {
        var stock = new Stock(command);
        var stockCreated = stockRepository.save(stock);
        return Optional.of(stockCreated);
    }
}
