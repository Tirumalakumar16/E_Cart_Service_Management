package com.orderapp.orderservice.feignclients;


import com.inventory.inventory.dtos.RequestDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.dtos.UpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Inventory-service" , path = "/inventory-app/api")
public interface InventoryFeignClient {

    @GetMapping("/inventory/product_name")
    public ResponseInventoryDto getByProductName(@RequestBody RequestDto requestDto);

    @PutMapping("/inventory/update")
    public ResponseInventoryDto updateInventory(@RequestBody UpdateRequest updateRequest);

    @GetMapping("/inventory/{product_name}")
    public List<ResponseInventoryDto> getByProductNameOnlyOne(@PathVariable("product_name") String  productName);

    @GetMapping("/inventory/{email}/{product}")
    public ResponseInventoryDto getBySellerEmailAndProduct(@PathVariable("email") String email,@PathVariable("product") String product);
}
