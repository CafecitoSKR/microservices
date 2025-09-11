package com.espino.notificationservice.infrastructure.kafka;

import com.espino.notificationservice.infrastructure.contracts.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final String BOOTSTRAP =
            System.getenv().getOrDefault("KAFKA_BOOTSTRAP", "localhost:9092");
    private static final String GROUP_ID =
            System.getenv().getOrDefault("KAFKA_GROUP_ID", "notification-service");

    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> consumerFactory() {
        JsonDeserializer<OrderPlacedEvent> json = new JsonDeserializer<>(OrderPlacedEvent.class);
        json.addTrustedPackages("com.espino.notificationservice.infrastructure.contracts");
        json.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), json);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent>
    kafkaListenerContainerFactory(ConsumerFactory<String, OrderPlacedEvent> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent>();
        factory.setConsumerFactory(consumerFactory);


        var backoff = new ExponentialBackOffWithMaxRetries(3);
        backoff.setInitialInterval(500);
        backoff.setMultiplier(2.0);
        backoff.setMaxInterval(5000);
        factory.setCommonErrorHandler(new DefaultErrorHandler((rec, ex) -> {}, backoff));
        return factory;
    }
}