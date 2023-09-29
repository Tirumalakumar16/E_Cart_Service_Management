package com.sellerservice.seller_service.dtos;

import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseAddressSellerDto {

    private SellerResponseDto sellerResponseDto;
    private List<ResponseAddressDto> addresses;
}
