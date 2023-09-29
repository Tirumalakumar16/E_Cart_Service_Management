package com.inventory.inventory.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {


        private Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "seller-service",groupId = "mirco-service")
    public  void kafkaConsumer1(String message) {

        logger.info("Consumer1 RX : {}",message);
    }

    @KafkaListener(topics = "seller-service",groupId = "mirco-service")
    public  void kafkaConsumer2(String message) {

        logger.info("Consumer2 RX : {}",message);
    }


    @KafkaListener(topics = "order-service",groupId = "mirco-service")
    public  void kafkaConsumer3(String message) {

        logger.info("Consumer1 RX : {}",message);
    }

    @KafkaListener(topics = "order-service",groupId = "mirco-service")
    public  void kafkaConsumer4(String message) {

        logger.info("Consumer2 RX : {}",message);
    }
}
