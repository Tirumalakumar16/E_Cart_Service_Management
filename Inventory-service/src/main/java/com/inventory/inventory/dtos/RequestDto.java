package com.inventory.inventory.dtos;

import com.inventory.inventory.models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestDto {

   private String productName;
}
