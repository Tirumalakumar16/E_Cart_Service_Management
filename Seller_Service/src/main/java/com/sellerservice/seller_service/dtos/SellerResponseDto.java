package com.sellerservice.seller_service.dtos;

import jakarta.annotation.Nonnull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SellerResponseDto {

    private Date createdOn;
    private String sellerName;
    private int productsRegistered;
    private Date updatedOn;
    private String emailId;
    private String mobile;
}
