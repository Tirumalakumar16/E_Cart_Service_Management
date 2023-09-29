package com.ktkapp.customerservice.service;

import com.addcart.addcartservice.dtos.*;
import com.addcart.addcartservice.exception.CartDetailsNotFoundException;
import com.addcart.addcartservice.exception.EmailIdAlreadyRegistered;
import com.identityservice.dtos.IdentityResponseDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.CategoryNotExistsInInventory;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.customerservice.dtos.*;

import com.ktkapp.customerservice.dtos.inventory.RequestInventoryCustDto;
import com.ktkapp.customerservice.feignclients.*;

import com.ktkapp.customerservice.models.Customer;
import com.ktkapp.customerservice.repository.CustomerRepository;

import com.ktkapp.customerservice.service.kafka.KafkaPublisher;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import com.sellerservice.seller_service.dtos.SellerResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class CustomerServiceImpl implements CustomerService{

    private ModelMapper modelMapper;

    private CustomerRepository customerRepository;

    private AddressFeignClient addressFeignClient;

    private OrderFeignClient orderFeignClient;

   private InventoryFeignClient inventoryFeignClient;

   private AddCartFeignClient addCartFeignClient;

   private KafkaPublisher kafkaPublisher;
   private IdentityFeignClient identityFeignClient;

   private SellerFeignClient sellerFeignClient;

    @Autowired
    public CustomerServiceImpl(ModelMapper modelMapper,
                               CustomerRepository customerRepository,
                               AddressFeignClient addressFeignClient,
                               OrderFeignClient orderFeignClient,
                               InventoryFeignClient inventoryFeignClient,
                               AddCartFeignClient addCartFeignClient,
                               KafkaPublisher kafkaPublisher,
                               IdentityFeignClient identityFeignClient,
                               SellerFeignClient sellerFeignClient) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.addressFeignClient = addressFeignClient;
        this.orderFeignClient = orderFeignClient;
        this.inventoryFeignClient = inventoryFeignClient;
        this.addCartFeignClient = addCartFeignClient;
        this.kafkaPublisher = kafkaPublisher;
        this.identityFeignClient = identityFeignClient;
        this.sellerFeignClient = sellerFeignClient;
    }

    //*********************************** CUSTOMER PROFILE ************************************
//
    @Override
    public void saveCustomer(CustomerProfileRequest profileRequest,String username) throws EmailIdAlreadyRegistered {

       IdentityResponseDto responseDto = identityFeignClient.getUserCredentials(username);
        Customer customer1 = customerRepository.findByEmailId(responseDto.getEmailId());
        if(customer1 != null) {
            throw new EmailIdAlreadyRegistered("Email id already register in Application please do forget password...");
        }

        Customer customer = new Customer();
        if(profileRequest.getCustomerName() != null){
            customer.setCustomerName(profileRequest.getCustomerName());
        }
        if(profileRequest.getMobile() != null) {
            customer.setMobile(profileRequest.getMobile());
        }
        Customer customerEmail= customerRepository.findByEmailId(responseDto.getEmailId());

        if (customerEmail == null) {
            customer.setEmailId(responseDto.getEmailId());
        }

        Customer customer2= customerRepository.save(customer);

        kafkaPublisher.sendMessage("Customer : "+ customer2.getCustomerName()+" Added successfully...!");
    }

    @Override
    public ResponseCustomerProfileDto getByUserName(String userName) {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        Customer customer = customerRepository.findByEmailId(identityResponseDto.getEmailId());

        ResponseCustomerProfileDto responseCustomerDto= new ResponseCustomerProfileDto();

        responseCustomerDto.setCustomerName(customer.getCustomerName());
        responseCustomerDto.setMobile(customer.getMobile());
        responseCustomerDto.setEmailId(identityResponseDto.getEmailId());

        kafkaPublisher.sendMessage("Fetched the customer details , Customer name : "+customer.getCustomerName());
        return responseCustomerDto;
    }


//    ******************************************* ADD CART *************************************************

    @Override
    public ResponseAddCartDto AddToCart(RequestProduct requestProduct,String userName) {

        List<ResponseInventoryDto> inventory = inventoryFeignClient.getByProductNameOnlyOne(requestProduct.getProductName());

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmailId(identityResponseDto.getEmailId());

           RequestAddCartDto requestAddCartDto = new RequestAddCartDto();
           requestAddCartDto.setPrice(inventory.get(0).getPrice());
           requestAddCartDto.setQuantity(1);
           requestAddCartDto.setProductName(requestProduct.getProductName());
           requestAddCartDto.setEmailRequest(emailRequest);

        ResponseAddCartDto responseAddCartDto = addCartFeignClient.addToCart(requestAddCartDto);

        kafkaPublisher.sendMessage("Added to cart successfully : with Product name : "+requestProduct.getProductName()+
                " Quantiry :  1" );
        return responseAddCartDto;
    }

    @Override
    public  List<ResponseAddCartDto> getAllProductsFromCart(String username) throws CartDetailsNotFoundException {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(username);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmailId(identityResponseDto.getEmailId());

        List<ResponseAddCartDto> responseAddCartDtos = addCartFeignClient.getCartProductDetails(identityResponseDto.getEmailId());
        kafkaPublisher.sendMessage("Fetched the All products from cart with email : "+responseAddCartDtos.get(0).getEmailId());

        return responseAddCartDtos;
    }

    @Override
    public ResponseAddCartDto updateCart(UpdateCart updateCart, String userName) {
        ResponseAddCartDto responseAddCartDto;

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmailId(identityResponseDto.getEmailId());;
        updateCart.setEmailRequest(emailRequest);
               responseAddCartDto  = addCartFeignClient.updateCart(updateCart);

            return responseAddCartDto;
    }

    @Override
    public ResponseEntity<String> deleteCart(RequestProduct requestProduct,String userName) {
       IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        return addCartFeignClient.deleteCartProduct(requestProduct.getProductName(),identityResponseDto.getEmailId());
    }



    // *********************************************** ADDRESS ***************************************


    @Override
    public ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto, String username) {

        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(username);

        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());
        requestAddressDto.setEmailRequest(emailAddressRequest);

        return addressFeignClient.saveAddress(requestAddressDto);
    }

    @Override
    public List<ResponseAddressDto> getAddressesFromAddressService(String username) throws AddressNotFoundWithEmail {
        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(username);


        return addressFeignClient.getAddresses(identityResponseDto.getEmailId());
    }

    @Override
    public String deleteAddressWithZip(DeleteAddressRequest deleteAddressRequest,String userName) {

            IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(userName);

            EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
            emailAddressRequest.setEmailId(identityResponseDto.getEmailId());

            deleteAddressRequest.setEmailAddressRequest(emailAddressRequest);
        return addressFeignClient.deleteAddress(deleteAddressRequest);
    }


    @Override
    public ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest,String userName) {

        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(userName);

        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());

        updateAddressRequest.setEmailRequest(emailAddressRequest);

        return addressFeignClient.updateAddress(updateAddressRequest);
    }

    @Override
    public ResponseAddressDto getByZipAndEmail(RequestByZipAndEmailDto requestByZipAndEmailDto,String userName) {

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        return addressFeignClient.getByZipAddress(identityResponseDto.getEmailId(),requestByZipAndEmailDto.getHouseNumber(),requestByZipAndEmailDto.getZip());
    }


    // **************************************** ORDERS ********************************************


    @Override
    public CustomerOrderResponseDto placeOrderFromCust(CustomerOrderRequestDto customerOrderRequestDto,String userName) throws ProductsStockNotAvailableInInInventory {

        CustomerOrderResponseDto customerOrderResponseDto = new CustomerOrderResponseDto();

        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        Customer customer = customerRepository.findByEmailId(identityResponseDto.getEmailId());
        ResponseCustomerProfileDto responseCustomerProfileDto = modelMapper.map(customer,ResponseCustomerProfileDto.class);

        customerOrderResponseDto.setCustomerDetails(responseCustomerProfileDto);

       ResponseAddressDto responseAddressDto = addressFeignClient.getByZipAddress(identityResponseDto.getEmailId(),customerOrderRequestDto.getAddressRequest().getHouseNumber(),customerOrderRequestDto.getAddressRequest().getZip());
        customerOrderResponseDto.setShippingTo(responseAddressDto);


        RequestOrderDto requestOrderDto = new RequestOrderDto();

        List<ResponseInventoryDto> responseInventoryDtos =inventoryFeignClient.getByProductNameOnlyOne(customerOrderRequestDto.getProductName());
        PriorityQueue<ResponseInventoryDto> pq = new PriorityQueue<>(new ResponseInventoryDto.responseComp());
        pq.addAll(responseInventoryDtos);

        assert pq.peek() != null;
        SellerResponseDto sellerResponseDto = sellerFeignClient.getSellerByEmail(pq.peek().getSellerEmailId());

        requestOrderDto.setCustomerName(responseCustomerProfileDto.getCustomerName());
        requestOrderDto.setQuantity(customerOrderRequestDto.getQuantity());
        requestOrderDto.setProductName(customerOrderRequestDto.getProductName());
        requestOrderDto.setEmailId(responseCustomerProfileDto.getEmailId());
        requestOrderDto.setZip(customerOrderRequestDto.getAddressRequest().getZip());
        requestOrderDto.setHouseNumber(customerOrderRequestDto.getAddressRequest().getHouseNumber());
        requestOrderDto.setSellerName(sellerResponseDto.getSellerName());
        requestOrderDto.setSellerEmail(sellerResponseDto.getEmailId());

        ResponseOrderDto responseOrderDto = orderFeignClient.placeOrder(requestOrderDto);
        customerOrderResponseDto.setOrderDetails(responseOrderDto);

        return customerOrderResponseDto;

    }


    @Override
    public List<ResponseOrderDto> getAllOrders(String userName) throws OrdersNotPlaced {

        return orderFeignClient.getAllOrders(userName);

    }

    @Override
    public List<ResponseInventoryDto> getAllProductsFromInventoryByCategory(RequestInventoryCustDto requestInventoryCustDto) throws CategoryNotExistsInInventory {

        return inventoryFeignClient.getByCategory(requestInventoryCustDto.getCategory());
    }
}
