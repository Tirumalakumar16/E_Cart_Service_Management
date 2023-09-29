package com.ktkapp.customerservice.exceptions;

public class NotAddedToCartException extends Exception{
    public NotAddedToCartException(String message) {
        super(message);
    }
}
