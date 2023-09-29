package com.ktkapp.customerservice.dtos;

import com.ktkapp.customerservice.models.Customer;

import com.orderapp.orderservice.dtos.RequestOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RequestCustomerProductDto {

    private Customer customer;

    private RequestOrderDto requestOrderDto;


}
