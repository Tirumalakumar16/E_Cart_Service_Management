package com.addcart.addcartservice.service;

import com.addcart.addcartservice.dtos.*;

import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.addcart.addcartservice.exception.PleaseLoginAgainException;
import com.addcart.addcartservice.models.CartDetails;

import java.util.List;

public interface AddCartService {





    ResponseAddCartDto save(RequestAddCartDto requestAddCartDto,String email);

    List<ResponseAddCartDto> getByEmailId(String emailId) throws CartDetailsNotFoundException;

    void delete(String product,String email);


    ResponseAddCartDto updateCart(UpdateCart updateCart) ;

    ResponseAddCartDto getByProductNameAndEmail(String product ,String email);
}
