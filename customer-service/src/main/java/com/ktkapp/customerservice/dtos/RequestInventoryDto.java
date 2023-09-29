package com.ktkapp.customerservice.dtos;

import com.inventory.inventory.models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInventoryDto {

   private Inventory inventory;
}
