package com.inventory.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviser {


    @ExceptionHandler(CategoryNotExistsInInventory.class)
    public ResponseEntity<ErrorMessage> exceptionHandling1(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProduckNameNotExistsInInventory.class)
    public ResponseEntity<ErrorMessage> exceptionHandling2(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompanyNotExistsInInventory.class)
    public ResponseEntity<ErrorMessage> exceptionHandling3(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductsStockNotAvailableInInInventory.class)
    public ResponseEntity<ErrorMessage> exceptionHandling4(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SellerNotAvailable.class)
    public ResponseEntity<ErrorMessage> exceptionHandling5(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }




}
