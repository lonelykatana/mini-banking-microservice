package com.erick.minibankingmicroservice.controller;

import com.erick.minibankingmicroservice.dto.AccountRes;
import com.erick.minibankingmicroservice.dto.CreateAccReq;
import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.response.ApiResponse;
import com.erick.minibankingmicroservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/banking/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ApiResponse<AccountRes> createAccount(
            @Valid @RequestBody CreateAccReq req) {

        Account account = accountService.createAccount(
                req.getCustomerId(),
                req.getBranchId(),
                req.getBranchName()
        );

        return new ApiResponse<>(
                true,
                "Account created successfully",
                new AccountRes(account),
                LocalDateTime.now()
        );
    }

    @GetMapping("/{accountNumber}")
    public ApiResponse<AccountRes> getAccount(@PathVariable String accountNumber){

        Account account = accountService.getAccount(accountNumber);

        return new ApiResponse<>(
                true,
                "Account retrieved",
                new AccountRes(account),
                LocalDateTime.now()
        );
    }

    @GetMapping("/{accountNumber}/balance")
    public ApiResponse<BigDecimal> getBalance(@PathVariable String accountNumber){

        BigDecimal balance = accountService.getBalance(accountNumber);

        return new ApiResponse<>(
                true,
                "Balance retrieved",
                balance,
                LocalDateTime.now()
        );
    }
}
