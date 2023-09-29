package com.addcart.addcartservice.models;

import brave.internal.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetails {
    @jakarta.persistence.Id
    @Id
    @SequenceGenerator(
            name = "addcart_sequence",
            sequenceName = "addcart_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String emailId;
    private String productName;
    private double price;
    private int quantity;


}
