package com.addcart.addcartservice.controller;

import com.addcart.addcartservice.dtos.*;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.addcart.addcartservice.exception.EmailIdNotFoundException;
import com.addcart.addcartservice.exception.PleaseLoginAgainException;
import com.addcart.addcartservice.models.CartDetails;
import com.addcart.addcartservice.repository.AddCartRepository;
import com.addcart.addcartservice.service.AddCartService;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import feign.form.ContentType;
import jakarta.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddCartController {

    private AddCartService addCartService;
    private KafkaEvenController controller;
    @Autowired
    public AddCartController(AddCartService addCartService, KafkaEvenController controller) {
        this.addCartService = addCartService;
        this.controller = controller;
    }

    @PostMapping("/add_cart")
    public ResponseAddCartDto addToCart(@RequestBody RequestAddCartDto requestAddCartDto) {
        //Adding the Cart to the Service.

        return addCartService.save(requestAddCartDto,requestAddCartDto.getEmailRequest().getEmailId());
    }

    @GetMapping( "/add_cart/{email}")
    public List<ResponseAddCartDto> getCartProductDetails(@PathVariable("email") String email) throws CartDetailsNotFoundException {


        //Searching the Cart by email Address and Getting List cart details.

         return addCartService.getByEmailId(email);
    }
    @DeleteMapping("/add_cart/{product}/{email}")
    public ResponseEntity<String> deleteCartProduct( @PathVariable("product") String product,@PathVariable("email") String email) {
        // Deleting the entire cart with having email cart service.
        addCartService.delete(product,email);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted from Cart...!");
    }

    @PutMapping("/add_cart")
    public ResponseAddCartDto updateCart(@RequestBody UpdateCart updateCart)  {

        // Here we pass the updateCart class as request body having fields like the
        //1.product name
        //2.quantity
        //3.Operation enum (Update, Remove)

        return addCartService.updateCart(updateCart);
    }
    @GetMapping("/add_cart/{product}/{email}")
    public ResponseAddCartDto getByProductNameAndEmail(@PathVariable("product") String product,@PathVariable("email") String email ) {

        return addCartService.getByProductNameAndEmail(product,email);
    }

}
