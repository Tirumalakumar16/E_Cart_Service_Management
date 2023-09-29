package com.sellerservice.seller_service.openfeign;

import com.identityservice.dtos.IdentityResponseDto;
import com.ktkapp.addressservice.dtos.EmailAddressRequest;
import com.ktkapp.addressservice.dtos.RequestAddressDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Address-service", path = "/address-app/api")
public interface AddressFeignClient {
    @PostMapping("/address")
    public ResponseAddressDto saveAddress(@RequestBody RequestAddressDto requestAddressDto);

    @GetMapping("/addresses/{email}")
    public List<ResponseAddressDto> getAddresses(@PathVariable("email")String emailId) throws AddressNotFoundWithEmail;
}
