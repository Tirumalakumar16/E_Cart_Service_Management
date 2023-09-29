package com.ktkapp.customerservice.feignclients;


import com.addcart.addcartservice.dtos.*;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "AddCart-service" , path = "/add_cart-app/api")
public interface AddCartFeignClient {

    @GetMapping("/add_cart/{product}/{email}")
    public ResponseAddCartDto getByProductNameAndEmail(@PathVariable("product") String product,@PathVariable("email") String email );

    @PutMapping("/add_cart")
    public ResponseAddCartDto updateCart(@RequestBody UpdateCart updateCart);

    @DeleteMapping("/add_cart/{product}/{email}")
    public ResponseEntity<String> deleteCartProduct( @PathVariable("product") String product,@PathVariable("email") String email);

    @GetMapping(value = "/add_cart/{email}")
    public List<ResponseAddCartDto> getCartProductDetails(@PathVariable("email") String email) throws CartDetailsNotFoundException;

    @PostMapping("/add_cart")
    public ResponseAddCartDto addToCart(@RequestBody RequestAddCartDto requestAddCartDto);
}
