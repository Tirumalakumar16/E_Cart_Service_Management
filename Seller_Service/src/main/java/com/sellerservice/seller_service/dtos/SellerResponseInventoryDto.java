package com.sellerservice.seller_service.dtos;

import com.inventory.inventory.dtos.ResponseInventoryDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SellerResponseInventoryDto {

    private SellerResponseDto sellerResponseDto;

    private ResponseInventoryDto responseInventoryDto;
}
