package com.ktkapp.customerservice.dtos.order;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderProduct {
    private String productName;
    private int quantity;
}
