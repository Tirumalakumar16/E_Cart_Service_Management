package com.ktkapp.customerservice.controller;

import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.addcart.addcartservice.dtos.TotalCart;
import com.addcart.addcartservice.dtos.UpdateCart;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ktkapp.customerservice.dtos.*;
import com.ktkapp.customerservice.exceptions.NotAddedToCartException;
import com.ktkapp.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
public class CustomerAddCartController {


    private CustomerService customerService;

    @Autowired
    public CustomerAddCartController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer/add_to_cart")
    public ResponseAddCartDto AddingToCart(@RequestBody RequestProduct requestProduct,@RequestHeader("LoggedInUser") String userName) {

        ResponseAddCartDto responseAddCartDto = null;
        try {
            responseAddCartDto = customerService.AddToCart(requestProduct,userName);
            if(responseAddCartDto == null) {
                throw new NotAddedToCartException("The selected product not added to cart please check details once..!");
            }
        } catch (NotAddedToCartException e ) {
            System.out.println(e.getMessage());
        }
        return responseAddCartDto;
    }
    @GetMapping(value = "/customer/add_carts" )
    public  List<ResponseAddCartDto> getAllProductsFromCart(@RequestHeader("LoggedInUser") String userName) throws CartDetailsNotFoundException {

        return customerService.getAllProductsFromCart(userName);
    }

    @PutMapping("/customer/add_cart")
    public ResponseAddCartDto updatecart(@RequestBody UpdateCart updateCart,@RequestHeader("LoggedInUser") String userName) {

        return customerService.updateCart(updateCart,userName);
    }


    @DeleteMapping ("/customer/add_cart/delete")
    public ResponseEntity<String> deleteCartByEmail(@RequestBody RequestProduct requestProduct,@RequestHeader("LoggedInUser") String userName) {

        return customerService.deleteCart(requestProduct,userName);
    }


}
