package com.orderapp.orderservice.service;

import com.addcart.addcartservice.dtos.EmailRequest;
import com.addcart.addcartservice.dtos.RequestCartDto;
import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.addcart.addcartservice.models.CartDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identityservice.dtos.IdentityResponseDto;
import com.inventory.inventory.dtos.RequestDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.ktkapp.addressservice.dtos.RequestByZipAndEmailDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.orderapp.orderservice.dtos.RequestOrderCartDto;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import com.orderapp.orderservice.feignclients.AddCartFeignClient;
import com.orderapp.orderservice.feignclients.AddressFeignClient;
import com.orderapp.orderservice.feignclients.IdentityFeignClient;
import com.orderapp.orderservice.feignclients.InventoryFeignClient;
import com.orderapp.orderservice.models.OrderStatus;
import com.orderapp.orderservice.models.Orders;
import com.orderapp.orderservice.repository.OrderRepository;
import com.orderapp.orderservice.service.kafka.KafkaPublisher;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.apache.kafka.common.protocol.types.Field;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository ordeRepository;
    private ModelMapper modelMapper;
    private KafkaPublisher kafkaPublisher;


    private InventoryFeignClient inventoryFeignClient;

    private AddCartFeignClient addCartFeignClient;

    private AddressFeignClient addressFeignClient;

    private IdentityFeignClient identityFeignClient;

    @Autowired
    public OrderServiceImpl(OrderRepository ordeRepository, ModelMapper modelMapper, KafkaPublisher kafkaPublisher, InventoryFeignClient inventoryFeignClient, AddCartFeignClient addCartFeignClient, AddressFeignClient addressFeignClient, IdentityFeignClient identityFeignClient) {
        this.ordeRepository = ordeRepository;
        this.modelMapper = modelMapper;
        this.kafkaPublisher = kafkaPublisher;
        this.inventoryFeignClient = inventoryFeignClient;
        this.addCartFeignClient = addCartFeignClient;
        this.addressFeignClient = addressFeignClient;
        this.identityFeignClient = identityFeignClient;
    }

    @Override
    public ResponseOrderDto placeOrder(RequestOrderDto requestOrderDto) throws ProductsStockNotAvailableInInInventory {

        //for product
        RequestDto requestDto = new RequestDto();
        requestDto.setProductName(requestOrderDto.getProductName());
        //for address

        //inventory
        ResponseInventoryDto responseInventoryDtos = inventoryFeignClient.getBySellerEmailAndProduct(requestOrderDto.getSellerEmail(), requestOrderDto.getProductName());

        if ((responseInventoryDtos.getQuantity() < requestOrderDto.getQuantity())) {
            throw new ProductsStockNotAvailableInInInventory("Stock is not available...."+requestOrderDto.getProductName());
        }
        //for updating the inventory
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setProductName(requestOrderDto.getProductName());
        updateRequest.setQuantity(requestOrderDto.getQuantity());
        updateRequest.setSellerEmailId(responseInventoryDtos.getSellerEmailId());
        ResponseInventoryDto responseInventoryDto = inventoryFeignClient.updateInventory(updateRequest);

        //for saving order to orders db
        Orders orders = new Orders();

        orders.setCategory(responseInventoryDtos.getCategory());
        orders.setOrderedOn(new Date());
        orders.setEmailId(requestOrderDto.getEmailId());
        orders.setOrderStatus(OrderStatus.Booked);
        orders.setPrice(responseInventoryDtos.getPrice());
        orders.setCustomerName(requestOrderDto.getCustomerName());
        orders.setProductName(requestOrderDto.getProductName());
        orders.setQuantity(requestOrderDto.getQuantity());
        orders.setSellerEmailId(responseInventoryDtos.getSellerEmailId());
        orders.setHouseNumber(requestOrderDto.getHouseNumber());
        orders.setSellerName(requestOrderDto.getSellerName());
        orders.setZip(requestOrderDto.getZip());

       Orders orders1 = ordeRepository.save(orders);


        return modelMapper.map(orders1,ResponseOrderDto.class);
    }

    @Override
    public List<ResponseOrderDto> getAllOrders(String userName) throws OrdersNotPlaced {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);


       List<Orders>  orders = ordeRepository.findByEmailId(identityResponseDto.getEmailId());

       if (orders.isEmpty()){

           throw new OrdersNotPlaced("Orders are not placed yet please Order to get all Orders...");
       }


        return Arrays.asList(modelMapper.map(orders,ResponseOrderDto[].class));
    }
}
