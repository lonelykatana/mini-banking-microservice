package com.erick.minibankingmicroservice.controller;

import com.erick.minibankingmicroservice.dto.AccountRes;
import com.erick.minibankingmicroservice.dto.CustomerRes;
import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.response.ApiResponse;
import com.erick.minibankingmicroservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/banking/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{nik}")
    public ApiResponse<CustomerRes> getAccount(@PathVariable String nik){

        CustomerRes res = customerService.getCustomerByNik(nik);

        return new ApiResponse<>(
                true,
                "Customer retrieved",
                res,
                LocalDateTime.now()
        );
    }
}
