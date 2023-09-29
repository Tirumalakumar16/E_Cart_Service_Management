package com.addcart.addcartservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
//import com.google.gson.Gson;
//import feign.codec.Decoder;
//import feign.jackson.JacksonDecoder;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
//        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//        converter.setGson(gson);
//        List<MediaType> supportedMediaTypes = converter.getSupportedMediaTypes();
//        if (! supportedMediaTypes.contains(TEXT_PLAIN)) {
//            supportedMediaTypes = new ArrayList<>(supportedMediaTypes);
//            supportedMediaTypes.add(TEXT_PLAIN);
//            converter.setSupportedMediaTypes(supportedMediaTypes);
//        }
//        return converter;
//    }
}

