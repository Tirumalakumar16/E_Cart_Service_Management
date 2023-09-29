package com.ktkapp.customerservice.controller;

import com.addcart.addcartservice.exception.EmailIdAlreadyRegistered;
import com.ktkapp.customerservice.dtos.CustomerProfileRequest;
import com.ktkapp.customerservice.dtos.ResponseCustomerProfileDto;
import com.ktkapp.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerProfileController {


    private CustomerService customerService;

    @Autowired
    public CustomerProfileController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer/profile")
    public String saveCustomer(@RequestBody CustomerProfileRequest profileRequest, @RequestHeader("LoggedInUser") String username) {
        try {
            customerService.saveCustomer(profileRequest,username);
            return "Profile was updated....";
        } catch (EmailIdAlreadyRegistered e ) {
            System.out.println(e.getMessage());
        }
        return "Profile not updated...";
    }
    @GetMapping("/customer/profile_details")
    public ResponseCustomerProfileDto getCustomerName(@RequestHeader("LoggedInUser") String username) {
        System.out.println("LoggedIn User : "+username);
        return customerService.getByUserName(username);
    }


}
