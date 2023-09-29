package com.inventory.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestInventoryDto {

   private String productName;
   private double price;
   private String company;
   private int quantity;
   private String category;
   private Date createdOn;
   private Date updatedOn;
   private String sellerEmailId;
}
