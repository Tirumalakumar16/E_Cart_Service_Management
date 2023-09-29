package com.ktkapp.customerservice.service;

import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.addcart.addcartservice.dtos.UpdateCart;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.addcart.addcartservice.exception.EmailIdAlreadyRegistered;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.CategoryNotExistsInInventory;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.customerservice.dtos.*;
import com.ktkapp.customerservice.dtos.inventory.RequestInventoryCustDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface CustomerService {

    // ***************************************** CUSTOMER PROFILE**********************************
    void saveCustomer(CustomerProfileRequest profileRequest,String username) throws EmailIdAlreadyRegistered;

    ResponseCustomerProfileDto getByUserName(String userName);

   // ResponseCustomerProductDto saveCustomerWithProduct(RequestCustomerProductDto requestCustomerProductDto);

   // ResponseCustomerProductDto saveCustomerWithProduct(RequestProductDto requestProductDto, Customer customer);

   // ResponseProductDto saveProduct(RequestProductDto requestProductDto);

   // List<ResponseProductDto> getAll();

   // ResponseInventoryDto updateInventory(RequestProduct requestProduct) ;

    // ****************************************** ADD CART ***************************************************************


    ResponseAddCartDto AddToCart(RequestProduct requestProduct,String userName);

    List<ResponseAddCartDto> getAllProductsFromCart(String userName) throws CartDetailsNotFoundException;

    ResponseAddCartDto updateCart(UpdateCart updateCart,String userName);

    ResponseEntity<String> deleteCart(RequestProduct requestProduct,String userName);
    
    
    
    // ****************************************** ADDRESS *******************************************************

    ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto, String userName);

    List<ResponseAddressDto> getAddressesFromAddressService(String username) throws AddressNotFoundWithEmail;

    String deleteAddressWithZip(DeleteAddressRequest deleteAddressRequest,String userName);

    ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest,String userName);



    ResponseAddressDto getByZipAndEmail(RequestByZipAndEmailDto requestByZipAndEmailDto,String userName);







    //****************************************** ORDERS ***************************************************

    CustomerOrderResponseDto placeOrderFromCust(CustomerOrderRequestDto customerOrderRequestDto, String userName) throws ProductsStockNotAvailableInInInventory;

    List<ResponseOrderDto> getAllOrders(String userName) throws OrdersNotPlaced;






    //***************************************** INVENTORY ***************************************************

    List<ResponseInventoryDto> getAllProductsFromInventoryByCategory(RequestInventoryCustDto requestInventoryCustDto) throws CategoryNotExistsInInventory;
}
