package com.inventory.inventory.service;



import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.ProduckNameNotExistsInInventory;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;

import java.util.List;

public interface InventoryService {

    ResponseInventoryDto save(RequestInventoryDto requestDto);

    List<ResponseInventoryDto> getAll();

    List<ResponseInventoryDto> getByCategory(String category);

    ResponseInventoryDto getByCompany(String company);

    ResponseInventoryDto getByProductName(String productName);

    ResponseInventoryDto update(UpdateRequest request) throws ProductsStockNotAvailableInInInventory;

    List<ResponseInventoryDto> getByProductNameOnlyOne(String productName) throws ProduckNameNotExistsInInventory;

    List<ResponseInventoryDto> getBySellerEmail(String email) throws  SellerNotAvailable;

    ResponseInventoryDto getBySellerEmailAndProduct(String email, String product);
}
