package com.espino.notificationservice.infrastructure.kafka;

import com.espino.notificationservice.infrastructure.contracts.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPlacedEventListener {
    private final ObservationRegistry  observationRegistry;
    private final Tracer tracer;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message",this.observationRegistry).observe(()->{
            log.info("Received OrderPlacedEvent {}", orderPlacedEvent.getOrderId());
            log.info("TraceId- {}, Received Notification for Order - {}", Objects.requireNonNull(this.tracer.currentSpan()).context().traceId(),
                    orderPlacedEvent.getOrderId());
        });

    }
}
