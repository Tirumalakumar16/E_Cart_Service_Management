package com.ktkapp.customerservice.dtos;

import com.ktkapp.customerservice.models.Customer;
import com.orderapp.orderservice.dtos.RequestOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCustomerDto {

  private String emailId;
  private String CustomerName;
  private String mobile;

  
}
