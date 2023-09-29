package com.ktkapp.customerservice.feignclients;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.CategoryNotExistsInInventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Inventory-service" , path = "/inventory-app/api")
public interface InventoryFeignClient {

    @GetMapping("/inventory/{product_name}")
    public List<ResponseInventoryDto> getByProductNameOnlyOne(@PathVariable("product_name") String  productName);

    @PutMapping("/inventory")
    public ResponseInventoryDto updateInventory(@RequestBody UpdateRequest updateRequest);

    @GetMapping("/inventory/category/{category}")
    public List<ResponseInventoryDto> getByCategory(@PathVariable("category") String category) throws CategoryNotExistsInInventory;
}
