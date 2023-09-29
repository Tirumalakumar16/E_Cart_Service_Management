package com.addcart.addcartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor@NoArgsConstructor
@ToString
public class RequestCartDto {

    private String productName;
   private String emailId;

    // we setting it in customer service



}
