package com.espino.inventoryservice.inventory.application.internal.commandservices;

import com.espino.inventoryservice.inventory.domain.aggregates.commands.CreateReservationCommand;
import com.espino.inventoryservice.inventory.domain.aggregates.entities.ReservationItem;
import com.espino.inventoryservice.inventory.domain.aggregates.model.StockReservation;
import com.espino.inventoryservice.inventory.domain.services.StockReservationCommandService;
import com.espino.inventoryservice.inventory.infrastructure.persistence.jpa.repositories.StockReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockReservationCommandServiceImpl  implements StockReservationCommandService {
    private final StockReservationRepository repository;

    public StockReservationCommandServiceImpl(StockReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<StockReservation> handle(CreateReservationCommand command) {
        var stockReservation = new StockReservation(command.orderId());
        var drafts = command.items().stream().map(
                reservationItemDraft ->
                        new ReservationItem(reservationItemDraft.productId(), reservationItemDraft.quantity(),stockReservation)
        ).toList();
        stockReservation.setReservationItems(drafts);
        repository.save(stockReservation);
        return Optional.of(stockReservation);
    }
}

