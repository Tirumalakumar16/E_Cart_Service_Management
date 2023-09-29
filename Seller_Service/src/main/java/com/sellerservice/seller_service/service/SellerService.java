package com.sellerservice.seller_service.service;

import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;
import com.ktkapp.addressservice.dtos.DeleteAddressRequest;
import com.ktkapp.addressservice.dtos.RequestAddressDto;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.sellerservice.seller_service.dtos.*;
import com.sellerservice.seller_service.exceptions.SellerNotPresentException;
import com.sellerservice.seller_service.exceptions.UserNotRegiseredAsSellerException;

import java.util.List;

public interface SellerService {
    SellerResponseDto saveSeller(SellerRequestDto sellerRequestDto,String userName) throws SellerNotPresentException;

    SellerResponseDto getByEmail(String userName) throws SellerNotPresentException;

    List<ResponseInventoryDto> getAllInventory(String emailRequestSellerDto) throws SellerNotAvailable;

    SellerResponseInventoryDto saveInventory(RequestInventoryDto requestInventoryDto,String userName) throws SellerNotPresentException;

    ResponseSellerAddressDto saveAddress(RequestAddressDto requestAddressDto, String userName) throws SellerNotPresentException, UserNotRegiseredAsSellerException;

    SellerResponseDto getSellerByEmail(String emailId) throws SellerNotPresentException;

    ResponseInventoryDto updateInventory(UpdateRequest updateQuantityDto,String userName) throws ProductsStockNotAvailableInInInventory, SellerNotPresentException;

    ResponseAddressSellerDto getAllAddressesByEmail(String userName) throws SellerNotPresentException, AddressNotFoundWithEmail;

    String deleteAddress(DeleteAddressRequest deleteAddressRequest, String userName);
}
