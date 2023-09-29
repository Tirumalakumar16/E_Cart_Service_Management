package com.inventory.inventory.controller;


import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.*;
import com.inventory.inventory.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventory")
    public ResponseInventoryDto saveInventory(@RequestBody RequestInventoryDto requestDto) {

        return inventoryService.save(requestDto);
    }

    @GetMapping("/inventories")
    public List<ResponseInventoryDto> getAll() {

        return inventoryService.getAll();
    }

    @GetMapping("/inventory/category/{category}")
    public List<ResponseInventoryDto> getByCategory(@PathVariable("category") String category) throws CategoryNotExistsInInventory {

        List<ResponseInventoryDto> responseInventoryDtos = inventoryService.getByCategory(category);
        if (responseInventoryDtos.isEmpty()) {
            throw new CategoryNotExistsInInventory("Searched category not existed in inventory database....!");
        }

        return responseInventoryDtos;
    }

        @GetMapping("/inventory/company/{company}")
    public ResponseInventoryDto getByCompany(@PathVariable("company") String company) throws CompanyNotExistsInInventory {

            ResponseInventoryDto  responseInventoryDtos= inventoryService.getByCompany(company);
            if(responseInventoryDtos == null) {
                throw new CompanyNotExistsInInventory("Searched Company not existed in inventory database....!");
            }
        return responseInventoryDtos;
    }

    @GetMapping("/inventory/product_name/{productName}")
    public ResponseInventoryDto getByProductName(@PathVariable("productName") String product) throws ProduckNameNotExistsInInventory {

            ResponseInventoryDto    responseInventoryDtos= inventoryService.getByProductName(product);
            if(responseInventoryDtos == null){
                throw new ProduckNameNotExistsInInventory("Searched Product Name not existed in inventory database....!");
            }

        return responseInventoryDtos;
    }


    @PutMapping("/inventory/update")
    public ResponseInventoryDto updateInventory(@RequestBody UpdateRequest updateRequest) throws ProductsStockNotAvailableInInInventory {


        return inventoryService.update(updateRequest);
    }
    @GetMapping("/inventory/{product_name}")
    public List<ResponseInventoryDto> getByProductNameOnlyOne(@PathVariable("product_name") String  productName) throws ProduckNameNotExistsInInventory {


        return inventoryService.getByProductNameOnlyOne(productName);
    }

    @GetMapping("/inventory/email/{email}")
    public List<ResponseInventoryDto> getBySellerEmail(@PathVariable("email")  String email) throws  SellerNotAvailable {

        return inventoryService.getBySellerEmail(email);
    }
    @GetMapping("/inventory/{email}/{product}")
    public ResponseInventoryDto getBySellerEmailAndProduct(@PathVariable("email") String email,@PathVariable("product") String product) {
        return inventoryService.getBySellerEmailAndProduct(email,product);
    }
}
