package com.orderapp.orderservice.feignclients;


import com.identityservice.dtos.IdentityResponseDto;
import com.inventory.inventory.dtos.RequestDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.dtos.UpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Identity-service" , path = "/identity-app/api")
public interface IdentityFeignClient {


    @GetMapping("/identity")
    public IdentityResponseDto getUserCredentials(@RequestHeader("loggedInUser") String userName);

}
