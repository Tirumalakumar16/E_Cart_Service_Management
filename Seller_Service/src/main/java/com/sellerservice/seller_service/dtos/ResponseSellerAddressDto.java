package com.sellerservice.seller_service.dtos;

import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseSellerAddressDto {

    private SellerResponseDto sellerResponseDto;
    private ResponseAddressDto responseAddressDto;
}
