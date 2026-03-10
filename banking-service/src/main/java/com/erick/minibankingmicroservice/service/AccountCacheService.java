package com.erick.minibankingmicroservice.service;

import java.math.BigDecimal;

public interface AccountCacheService {

    BigDecimal getBalance(String accountNumber);

    void saveBalance(String accountNumber, BigDecimal balance);

    void evictBalance(String accountNumber);
}
