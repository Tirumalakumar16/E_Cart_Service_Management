package com.orderapp.orderservice.service;

import com.addcart.addcartservice.dtos.RequestCartDto;
import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.orderapp.orderservice.dtos.RequestOrderCartDto;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import com.orderapp.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {


    ResponseOrderDto placeOrder(RequestOrderDto requestOrderDto) throws ProductsStockNotAvailableInInInventory;


    List<ResponseOrderDto> getAllOrders(String userName) throws OrdersNotPlaced;
}
