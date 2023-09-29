package com.addcart.addcartservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic(){

        return new NewTopic("add_cart-service",3,(short) 1);
    }
}
