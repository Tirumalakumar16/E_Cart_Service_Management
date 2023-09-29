package com.addcart.addcartservice.repository;

import com.addcart.addcartservice.dtos.ResponseAddCartDto;
import com.addcart.addcartservice.models.CartDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddCartRepository extends JpaRepository<CartDetails,Long> {


//    @Query("select new com.addcart.addcartservice.dtos.ResponseAddCartDto(a.emailId,a.productName,a.price,a.quantity) from CartDetails a where a.emailId=?1")
    @Query(value = "select * from add_cart_service.cart_details a where  a.email_id=?1",nativeQuery = true)
    List<CartDetails> findAllByEmailId(String email);
    List<CartDetails> findByProductName(String product);

    void deleteByProductName(String product);
    @Modifying
    @Transactional
    @Query(value = "delete from add_cart_service.cart_details a where a.email_id = ?1 and  a.product_name=?2",nativeQuery = true)
    void deleteByProductNameAndEmailId(String emailId , String productName);
    @Query(value = "select * from add_cart_service.cart_details a where a.product_name = ?1 and a.email_id = ?2",nativeQuery = true)
    CartDetails findByProductNameAndEmailId(String productName , String email);


}
