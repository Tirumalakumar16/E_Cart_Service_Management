package com.addcart.addcartservice.dtos;

import com.addcart.addcartservice.models.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor@NoArgsConstructor
@ToString
public class RequestAddCartDto {

    private String productName;
    private double price;
    private int quantity;

    // we setting it in customer service
   private  EmailRequest emailRequest;

    public String getProductName() {
        return productName;
    }

    public RequestAddCartDto setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public RequestAddCartDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public RequestAddCartDto setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public EmailRequest getEmailRequest() {
        return emailRequest;
    }

    public RequestAddCartDto setEmailRequest(EmailRequest emailRequest) {
        this.emailRequest = emailRequest;
        return this;
    }
}
