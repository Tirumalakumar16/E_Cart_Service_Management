package com.orderapp.orderservice.exceptions;

import com.inventory.inventory.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviser {


    @ExceptionHandler(OrdersNotPlaced.class)
    public ResponseEntity<ErrorMessage> exceptionHandling1(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }





}
