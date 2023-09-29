package com.sellerservice.seller_service.repository;


import com.sellerservice.seller_service.models.Seller;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

  //  @Query(value ="select * from e_cart_seller_service a where a.email_id=?1 " ,nativeQuery = true)
    Optional<Seller> findByEmailId(String emailId);

  @Override
  <S extends Seller> List<S> findAll(Example<S> example);
}
