package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.entity.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {

    Account createAccount(UUID customerId, String branchId, String branchName);

    Account getAccount(String accountNumber);

    BigDecimal getBalance(String accountNumber);
}
