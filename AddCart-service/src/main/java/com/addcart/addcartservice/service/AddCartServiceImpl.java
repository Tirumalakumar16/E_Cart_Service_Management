package com.addcart.addcartservice.service;

import com.addcart.addcartservice.controller.KafkaEvenController;
import com.addcart.addcartservice.dtos.*;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.addcart.addcartservice.models.CartDetails;

import com.addcart.addcartservice.repository.AddCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class AddCartServiceImpl implements AddCartService{

    private AddCartRepository addCartRepository;

    private ModelMapper modelMapper;

    private KafkaEvenController kafkaEvenController;

    @Autowired
    public AddCartServiceImpl(AddCartRepository addCartRepository, ModelMapper modelMapper, KafkaEvenController kafkaEvenController) {
        this.addCartRepository = addCartRepository;
        this.modelMapper = modelMapper;
        this.kafkaEvenController = kafkaEvenController;
    }

    @Override
    public ResponseAddCartDto save(RequestAddCartDto requestAddCartDto, String emailRequest) {
        ResponseAddCartDto responseAddCartDto;


        CartDetails cartDetail = addCartRepository.findByProductNameAndEmailId(requestAddCartDto.getProductName(), emailRequest);
        if(cartDetail != null) {
            cartDetail.setQuantity(cartDetail.getQuantity()+1);
            cartDetail = addCartRepository.save(cartDetail);
            return  modelMapper.map(cartDetail, ResponseAddCartDto.class);
        }
        CartDetails cartDetails = new CartDetails();
        cartDetails.setEmailId(emailRequest);
        cartDetails.setPrice(requestAddCartDto.getPrice());
        cartDetails.setQuantity(requestAddCartDto.getQuantity());
        cartDetails.setProductName(requestAddCartDto.getProductName());

        CartDetails cartDetails1 = addCartRepository.save(cartDetails);

         responseAddCartDto = modelMapper.map(cartDetails1, ResponseAddCartDto.class);
        kafkaEvenController.sendMessageFrom_Add_Cart_Service("Product Name : "+responseAddCartDto.getProductName() +" is added to your Cart with Email : "+responseAddCartDto.getEmailId());
        return responseAddCartDto;
    }

    @Override
    public List<ResponseAddCartDto> getByEmailId(String emailRequest) throws CartDetailsNotFoundException {


            List<CartDetails> cartDetails = addCartRepository.findAllByEmailId(emailRequest);

            if (cartDetails.isEmpty()) {
                throw new CartDetailsNotFoundException("Please Add products to your cart");
            }
           List<ResponseAddCartDto> responseAddCartDto = Arrays.asList(modelMapper.map(cartDetails,ResponseAddCartDto[].class));


            kafkaEvenController.sendMessageFrom_Add_Cart_Service("Fetch the Products details from cart By email : " + emailRequest);


            return responseAddCartDto;

    }

    @Override
    public void delete(String product,String email) {
        // we can add exceptions here but we are consider this API is directly from cart

         addCartRepository.deleteByProductNameAndEmailId(email, product);
         kafkaEvenController.sendMessageFrom_Add_Cart_Service("Deleted the Product from cart : "+product);
    }



    @Override
    public ResponseAddCartDto updateCart(UpdateCart updateCart)  {


        CartDetails cartDetails = addCartRepository.findByProductNameAndEmailId(updateCart.getProductName(),updateCart.getEmailRequest().getEmailId());

        cartDetails.setQuantity(updateCart.getQuantity());
        cartDetails = addCartRepository.save(cartDetails);
       kafkaEvenController.sendMessageFrom_Add_Cart_Service("your cart successfully Updated with : Quantity = "+updateCart.getQuantity()+ " , for product name = "+updateCart.getProductName());

          return modelMapper.map(cartDetails, ResponseAddCartDto.class);
    }

    @Override
    public ResponseAddCartDto getByProductNameAndEmail(String product, String email) {

                CartDetails cartDetails = addCartRepository.findByProductNameAndEmailId(product,email);

        return modelMapper.map(cartDetails, ResponseAddCartDto.class);

    }
}
