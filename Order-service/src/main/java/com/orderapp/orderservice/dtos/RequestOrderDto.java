package com.orderapp.orderservice.dtos;

import com.ktkapp.addressservice.dtos.RequestByZipAndEmailDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orderapp.orderservice.models.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestOrderDto {

    private String customerName;

    private String productName;

    private String emailId;

    private int quantity;

    private String houseNumber;
    private String zip;
    private String sellerName;
    private String sellerEmail;


    
}
