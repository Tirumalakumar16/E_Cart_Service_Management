package com.ktkapp.customerservice.controller.kafka;

import com.ktkapp.customerservice.service.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaController {

        private KafkaPublisher kafkaPublisher;
    @Autowired
    public KafkaController(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }


    public void sendMessage(String message) {

        kafkaPublisher.sendMessage(message);
    }
}
