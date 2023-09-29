package com.ktkapp.customerservice.controller;

import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.ktkapp.customerservice.dtos.CustomerOrderRequestDto;
import com.ktkapp.customerservice.dtos.CustomerOrderResponseDto;
import com.ktkapp.customerservice.service.CustomerService;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerOrderController {

    private CustomerService customerService;
    @Autowired
    public CustomerOrderController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping ("/customer/place_order")
    public CustomerOrderResponseDto placeOrderFromCustomer(@RequestBody CustomerOrderRequestDto customerOrderRequestDto, @RequestHeader("LoggedInUser") String userName) {

        try {
            return customerService.placeOrderFromCust(customerOrderRequestDto,userName);
        } catch (ProductsStockNotAvailableInInInventory e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/customer/orders")
    public List<ResponseOrderDto> getAllOrders(@RequestHeader("LoggedInUser") String userName) throws OrdersNotPlaced {

        return customerService.getAllOrders(userName);

    }

}
