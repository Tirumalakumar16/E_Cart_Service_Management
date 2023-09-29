package com.sellerservice.seller_service.dtos;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SellerRequestDto {


    private String sellerName;
    private String mobile;
}
