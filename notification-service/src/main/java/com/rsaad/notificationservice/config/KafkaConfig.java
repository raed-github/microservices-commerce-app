package com.rsaad.notificationservice.config;

import com.rsaad.notificationservice.event.kafka.PlaceOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaConfig {

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(PlaceOrderEvent orderPlacedEvent) {
        // send out an email notification
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
