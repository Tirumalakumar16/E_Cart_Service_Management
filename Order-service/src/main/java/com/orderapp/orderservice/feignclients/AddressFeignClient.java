package com.orderapp.orderservice.feignclients;



import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.ktkapp.addressservice.dtos.RequestByZipAndEmailDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Address-service" , path = "/address-app/api")
public interface AddressFeignClient {

    @GetMapping("/address/get_address_by_zip")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ResponseAddressDto getByZipAddress(@RequestBody RequestByZipAndEmailDto request);

}
