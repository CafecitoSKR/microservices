package com.espino.notificationservice.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name("notificationTopic")
                .partitions(2)
                .replicas(1)
                .build();
    }


}
