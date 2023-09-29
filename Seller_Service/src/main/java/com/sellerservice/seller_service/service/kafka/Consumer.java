package com.sellerservice.seller_service.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {


        private Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "inventory-service",groupId = "mirco-service")
    public  void kafkaConsumer3(String message) {

        logger.info("Seller1 RX : {}",message);
    }

    @KafkaListener(topics = "inventory-service",groupId = "mirco-service")
    public  void kafkaConsumer7(String message) {

        logger.info("Seller2 RX : {}",message);
    }
    @KafkaListener(topics = "order-service",groupId = "mirco-service")
    public  void kafkaConsumer8(String message) {

        logger.info("Seller RX : {}",message);
    }


    @KafkaListener(topics = "customer-service",groupId = "mirco-service")
    public  void kafkaConsumer9(String message) {

        logger.info("Seller RX : {}",message);
    }


}
