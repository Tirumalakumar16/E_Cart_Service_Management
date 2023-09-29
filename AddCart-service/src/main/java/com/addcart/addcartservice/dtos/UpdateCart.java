package com.addcart.addcartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCart {

    private String productName;
    private int quantity;

    private EmailRequest emailRequest;

}
