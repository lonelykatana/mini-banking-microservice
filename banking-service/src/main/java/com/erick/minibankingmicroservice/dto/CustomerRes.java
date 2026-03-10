package com.erick.minibankingmicroservice.dto;

import com.erick.minibankingmicroservice.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRes {

    private String nationalId;
    private String name;

    public CustomerRes(Customer customer) {
        this.nationalId = customer.getNationalId();
        this.name = customer.getName();
    }
}
