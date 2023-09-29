package com.addcart.addcartservice.controller;

import com.addcart.addcartservice.service.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaEvenController {

    private KafkaPublisher kafkaPublisher;
    @Autowired
    public KafkaEvenController(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }
    @PostMapping("/add_cart/{publish}")
    public ResponseEntity<?> sendMessageFrom_Add_Cart_Service(@PathVariable("publish") String message) {

        try {
            kafkaPublisher.sendMessage(message);
            return ResponseEntity.ok("message sent successfully....!");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
