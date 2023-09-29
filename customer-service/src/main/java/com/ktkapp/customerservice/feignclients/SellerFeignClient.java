package com.ktkapp.customerservice.feignclients;

import com.identityservice.dtos.IdentityResponseDto;
import com.sellerservice.seller_service.dtos.SellerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Seller-service", path = "seller-app/api")
public interface SellerFeignClient {

    @GetMapping("/seller/{email}")
    public SellerResponseDto getSellerByEmail(@PathVariable("email") String emailId);


}
