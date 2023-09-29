package com.addcart.addcartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.HttpMessageConverter;

@Data
@NoArgsConstructor

public class ResponseAddCartDto {

    private String emailId;
    private String productName;
    private double price;
    private int quantity;


    public ResponseAddCartDto(String emailId, String productName, double price, int quantity) {
        this.emailId = emailId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
