package com.inventory.inventory.repository;

import com.inventory.inventory.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    @Query(value = "select * from inventory_service.inventory i where i.category=?1",nativeQuery = true)
    List<Inventory> findByCategoryIgnoreCase(String category);

    @Query(value = "select * from inventory_service.inventory i where i.company=?1",nativeQuery = true)
    Inventory findByCompanyIgnoreCase(String company);

    @Query(value = "select * from inventory_service.inventory i where i.product_name like concat(?1,'%') " ,nativeQuery = true)
    List<Inventory> findByProductNameIgnoreCase(String productName);
    @Query(value = "select * from inventory_service.inventory i where i.product_name=?1",nativeQuery = true)
    List<Inventory> findByProductNameOne(String productName);

    @Query(value ="select * from inventory_service.inventory i where i.product_name=?1" ,nativeQuery = true)
    Inventory findByProductName(String productName);
    @Query(value ="select * from inventory_service.inventory i where i.product_name=?1 and i.seller_email_id=?2" ,nativeQuery = true)
    Inventory findByProductNameAndSellerEmailId(String product,String sellerEmailId);
    @Query(value = "select  * from inventory_service.inventory i where i.seller_email_id =?1",nativeQuery = true)
    List<Inventory> findBySellerEmailId(String email);

    @Query(value = "update inventory_service.inventory i set i.quantity=?1 where (i.seller_email_id=?2 and i.product_name=?3)",nativeQuery = true)
    Inventory updateQuantity(int quantity, String sellerEmailId, String productName);
}
