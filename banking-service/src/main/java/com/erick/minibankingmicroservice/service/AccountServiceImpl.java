package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.exception.ApiException;
import com.erick.minibankingmicroservice.repository.AccountRepository;
import com.erick.minibankingmicroservice.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accRepo;
    private final AccountCacheService accountCacheService;

    @Override
    public Account createAccount(UUID customerId, String branchId, String branchName) {
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setBranchId(branchId);
        account.setBranchName(branchName);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());

        String accountNumber = AccountNumberGenerator.generate(branchId);
        account.setAccountNumber(accountNumber);

        return accRepo.save(account);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        BigDecimal cached = accountCacheService.getBalance(accountNumber);

        if(cached != null){
            return cached;
        }

        Account account = accRepo
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));

        accountCacheService.saveBalance(accountNumber, account.getBalance());

        return account.getBalance();
    }
}
