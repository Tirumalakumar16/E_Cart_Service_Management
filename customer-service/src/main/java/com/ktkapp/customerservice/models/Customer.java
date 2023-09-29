package com.ktkapp.customerservice.models;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email",
                        columnNames = ("emailId")
                )
        }
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String mobile;
    private String emailId;

}
