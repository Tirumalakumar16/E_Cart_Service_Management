package com.orderapp.orderservice.feignclients;


import com.addcart.addcartservice.dtos.EmailRequest;
import com.addcart.addcartservice.dtos.RequestCartDto;
import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.addcart.addcartservice.models.CartDetails;
import com.inventory.inventory.dtos.RequestDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import jakarta.ws.rs.Consumes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "AddCart-service" , path = "/add_cart-app/api")
public interface AddCartFeignClient {

    @GetMapping("/add_cart/{product}/{email}")
    public ResponseAddCartDto getByProductNameAndEmail(@PathVariable("product") String product, @PathVariable("email") String email );

    @GetMapping(value = "/add_cart/get_cart_details",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CartDetails> getCartProductDetails(@RequestBody EmailRequest emailRequest);
}
