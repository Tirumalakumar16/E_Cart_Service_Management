package com.ktkapp.customerservice.controller;

import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.CategoryNotExistsInInventory;
import com.inventory.inventory.service.InventoryService;
import com.ktkapp.customerservice.dtos.inventory.RequestInventoryCustDto;
import com.ktkapp.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerInventoryController {

        private CustomerService customerService;
    @Autowired
    public CustomerInventoryController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customer/inventory/category")
    public List<ResponseInventoryDto> GetProductsByCategory(@RequestBody RequestInventoryCustDto requestInventoryCustDto) throws CategoryNotExistsInInventory {

        return customerService.getAllProductsFromInventoryByCategory(requestInventoryCustDto);
    }
}
