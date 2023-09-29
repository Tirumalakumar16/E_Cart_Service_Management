package com.inventory.inventory.dtos;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseInventoryDto {

    private String productName;
    private double price;
    private String company;
    private int quantity;
    private String category;
    private Date createdOn;
    private Date updatedOn;
    private String sellerEmailId;

    public static class responseComp implements Comparator<ResponseInventoryDto> {

        @Override
        public int compare(ResponseInventoryDto o1, ResponseInventoryDto o2) {
            return o2.getQuantity()-o1.getQuantity();
        }
    }



}
