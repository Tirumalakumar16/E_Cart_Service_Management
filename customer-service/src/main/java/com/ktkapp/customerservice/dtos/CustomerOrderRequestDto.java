package com.ktkapp.customerservice.dtos;

import com.ktkapp.addressservice.dtos.RequestByZipAndEmailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderRequestDto {

    private String productName;
    private int quantity;
    private CustomerAddressRequest addressRequest;

}
