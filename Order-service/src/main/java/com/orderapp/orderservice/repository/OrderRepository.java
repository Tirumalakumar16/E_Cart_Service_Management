package com.orderapp.orderservice.repository;

import com.addcart.addcartservice.models.CartDetails;
import com.orderapp.orderservice.models.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query(value = "select * from order_service.orders where email_id=?1",nativeQuery = true)
    List<Orders> findByEmailId(String emailId);

}
