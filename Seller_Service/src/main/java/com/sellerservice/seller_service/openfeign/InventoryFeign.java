package com.sellerservice.seller_service.openfeign;

import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Inventory-service",path = "/inventory-app/api")
public interface InventoryFeign {

    @GetMapping("/inventory/email/{email}")
    public List<ResponseInventoryDto> getBySellerEmail(@PathVariable("email")  String email) throws SellerNotAvailable;

    @PostMapping("/inventory")
    public ResponseInventoryDto saveInventory(@RequestBody RequestInventoryDto requestDto);

    @PutMapping("/inventory/update")
    public ResponseInventoryDto updateInventory(@RequestBody UpdateRequest updateRequest) throws ProductsStockNotAvailableInInInventory;

}
