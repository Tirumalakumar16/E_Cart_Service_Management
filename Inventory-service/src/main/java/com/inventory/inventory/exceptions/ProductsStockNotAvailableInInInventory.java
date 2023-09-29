package com.inventory.inventory.exceptions;

public class ProductsStockNotAvailableInInInventory extends Exception{
    public ProductsStockNotAvailableInInInventory(String message) {
        super(message);
    }
}
