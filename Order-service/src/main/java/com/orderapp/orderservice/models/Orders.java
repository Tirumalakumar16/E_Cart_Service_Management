package com.orderapp.orderservice.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private double price;
    private String customerName;
    private String emailId;
    private int quantity;
    private String category;
    private Date orderedOn;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    private String SellerName;
    private String sellerEmailId;
    private String houseNumber;
    private String zip;


}
