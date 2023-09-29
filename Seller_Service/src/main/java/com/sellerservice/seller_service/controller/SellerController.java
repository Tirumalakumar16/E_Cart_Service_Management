package com.sellerservice.seller_service.controller;

import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;
import com.ktkapp.addressservice.dtos.RequestAddressDto;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.sellerservice.seller_service.dtos.*;
import com.sellerservice.seller_service.exceptions.SellerNotPresentException;
import com.sellerservice.seller_service.exceptions.UserNotRegiseredAsSellerException;
import com.sellerservice.seller_service.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/seller")
    public SellerResponseDto saveSeller(@RequestBody SellerRequestDto sellerRequestDto,@RequestHeader("LoggedInUser") String userName) throws SellerNotPresentException {

        return sellerService.saveSeller(sellerRequestDto,userName);
    }

    @GetMapping("/seller")
    public SellerResponseDto getSeller(@RequestHeader("LoggedInUser") String userName) throws SellerNotPresentException {

        return sellerService.getByEmail(userName);
    }

    @PostMapping("/seller/inventory")
    public ResponseEntity<SellerResponseInventoryDto> addProductToInventory(@RequestBody RequestInventoryDto requestInventoryDto,@RequestHeader("loggedInUser")String userName) throws SellerNotPresentException {


        return ResponseEntity.status(HttpStatus.OK).body(sellerService.saveInventory(requestInventoryDto,userName));
    }
    @GetMapping("/seller/inventory")
    public List<ResponseInventoryDto> getAll(@RequestHeader("LoggedInUser") String userName) throws SellerNotAvailable {

        return sellerService.getAllInventory(userName);
    }



    @GetMapping("/seller/{email}")
    public SellerResponseDto getSellerByEmail(@PathVariable("email") String emailId) throws SellerNotPresentException {

        return sellerService.getSellerByEmail(emailId);
    }

    @PutMapping("/seller/inventory")
    public ResponseInventoryDto updateInventory(@RequestBody UpdateRequest updateQuantityDto,@RequestHeader("LoggedInUser") String userName) throws ProductsStockNotAvailableInInInventory, SellerNotPresentException {

        return sellerService.updateInventory(updateQuantityDto,userName);
    }


    @PostMapping("/seller/address")
    public ResponseSellerAddressDto saveAddress(@RequestBody RequestAddressDto requestAddressDto,@RequestHeader("LoggedInUser") String userName) throws UserNotRegiseredAsSellerException, SellerNotPresentException {

        return sellerService.saveAddress(requestAddressDto,userName);

    }


    @GetMapping("/seller/addresses")
    public ResponseAddressSellerDto getAllAddressesByEmail(@RequestHeader("LoggedInUser") String userName) throws AddressNotFoundWithEmail, SellerNotPresentException {

        return sellerService.getAllAddressesByEmail(userName);
    }



}
