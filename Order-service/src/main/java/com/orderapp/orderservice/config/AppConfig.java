package com.orderapp.orderservice.config;


import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
//@Bean
//public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
//    GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//    converter.setGson(gson);
//    List<MediaType> supportedMediaTypes = converter.getSupportedMediaTypes();
//    if (! supportedMediaTypes.contains(TEXT_PLAIN)) {
//        supportedMediaTypes = new ArrayList<>(supportedMediaTypes);
//        supportedMediaTypes.add(TEXT_PLAIN);
//        converter.setSupportedMediaTypes(supportedMediaTypes);
//    }
//    return converter;
//}
}
