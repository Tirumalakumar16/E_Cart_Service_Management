package com.sellerservice.seller_service.openfeign;

import com.identityservice.dtos.IdentityResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Identity-service", path = "identity-app/api")
public interface IdentityFeignClient {


    @GetMapping("/identity")
    public IdentityResponseDto getUserCredentials(@RequestHeader("loggedInUser") String userName);
}
