package com.orderapp.orderservice.controller;


import com.addcart.addcartservice.dtos.RequestCartDto;
import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orderapp.orderservice.dtos.RequestOrderCartDto;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import com.orderapp.orderservice.service.OrderService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/place_order")
    public ResponseOrderDto placeOrder(@RequestBody RequestOrderDto requestOrderDto) throws ProductsStockNotAvailableInInInventory {

        return orderService.placeOrder(requestOrderDto);
    }
    @GetMapping("/orders/{userName}")
    public List<ResponseOrderDto> getAllOrders(@PathVariable("userName") String userName) throws OrdersNotPlaced {
        return orderService.getAllOrders(userName);
    }








}
