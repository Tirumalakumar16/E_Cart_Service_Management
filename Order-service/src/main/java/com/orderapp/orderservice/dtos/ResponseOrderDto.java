package com.orderapp.orderservice.dtos;

import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orderapp.orderservice.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderDto {

    private String productName;
    private double price;
    private String customerName;
    private String emailId;
    private int quantity;
    private String category;
    private Date orderedOn;
    private OrderStatus orderStatus;
    private String SellerName;
    private String sellerEmailId;
    private String houseNumber;
    private String zip;

}
