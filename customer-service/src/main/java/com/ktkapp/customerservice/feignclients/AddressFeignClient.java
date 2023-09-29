package com.ktkapp.customerservice.feignclients;

import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Address-service",path = "/address-app/api")

public interface AddressFeignClient {

    @PostMapping("/address")
    public ResponseAddressDto saveAddress(@RequestBody RequestAddressDto requestAddressDto);

    @GetMapping("/addresses/{email}")
    public List<ResponseAddressDto> getAddresses(@PathVariable("email") String emailId) throws AddressNotFoundWithEmail;

    @DeleteMapping("/address")
    public String deleteAddress(@RequestBody DeleteAddressRequest deleteAddress);

    @PutMapping("/address")
    public ResponseAddressDto updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest);

    @GetMapping("/address/{email}/{houseNumber}/{zip}")
    public ResponseAddressDto getByZipAddress(@PathVariable("email") String email,@PathVariable("houseNumber") String houseNumber,@PathVariable("zip")String zip);
}
