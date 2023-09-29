package com.sellerservice.seller_service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaNewTopic {
    @Bean
    public NewTopic topic(){
        return new NewTopic("seller-service",3,(short)1);
    }
}
