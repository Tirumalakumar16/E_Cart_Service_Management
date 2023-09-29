package com.ktkapp.customerservice.feignclients;


import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import com.orderapp.orderservice.dtos.ResponseOrderDto;
import com.orderapp.orderservice.exceptions.OrdersNotPlaced;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import lombok.Builder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(name = "Order-service",path = "/order-app/api")

public interface OrderFeignClient {


    @PostMapping("/order/place_order")
    public ResponseOrderDto placeOrder(@RequestBody RequestOrderDto requestOrderDto) throws ProductsStockNotAvailableInInInventory;

    @GetMapping("/orders/{userName}")
    public List<ResponseOrderDto> getAllOrders(@PathVariable("userName") String userName)  throws OrdersNotPlaced;

}
