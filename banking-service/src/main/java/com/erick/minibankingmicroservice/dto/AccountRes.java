package com.erick.minibankingmicroservice.dto;

import com.erick.minibankingmicroservice.entity.Account;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountRes {

    private String customerId;
    private String branchId;
    private String branchName;
    private String accountNumber;
    private BigDecimal balance;

    public AccountRes(Account account) {
        this.customerId = String.valueOf(account.getCustomerId());
        this.branchId = account.getBranchId();
        this.branchName = account.getBranchName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
