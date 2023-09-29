package com.sellerservice.seller_service.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private Date createdOn;
    @Nonnull
    private String sellerName;
    @Nonnull
    private int productsRegistered;
    private Date updatedOn;
    private String emailId;
    private String mobile;

    private String sellerCompany;


}
