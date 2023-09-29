package com.ktkapp.customerservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseCustomerProfileDto {

    private String customerName;
    private String mobile;
    private String emailId;




}
