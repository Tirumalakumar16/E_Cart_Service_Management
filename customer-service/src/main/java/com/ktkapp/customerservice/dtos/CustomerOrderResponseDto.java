package com.ktkapp.customerservice.dtos;

import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderResponseDto {

    private ResponseCustomerProfileDto customerDetails;

    private ResponseOrderDto orderDetails;
    private ResponseAddressDto ShippingTo;
}
