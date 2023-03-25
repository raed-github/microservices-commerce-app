package com.rsaad.notificationservice.service.impl;

import com.rsaad.notificationservice.event.kafka.PlaceOrderEvent;
import com.rsaad.notificationservice.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {
    @Override
    public void sentEmail(String message) {
        log.info("Email Sent");
        System.out.println("Email Sent");
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(PlaceOrderEvent placeOrderEvent) {
        // send out an email notification
        log.info("Notifying user about Order - {}", placeOrderEvent.getOrderNumber());
    }

}
