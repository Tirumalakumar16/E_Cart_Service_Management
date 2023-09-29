package com.addcart.addcartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteRequestDto {


    private String productName;

    private EmailRequest emailRequest;
}
