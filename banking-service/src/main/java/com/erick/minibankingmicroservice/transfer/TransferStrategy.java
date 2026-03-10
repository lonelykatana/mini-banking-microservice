package com.erick.minibankingmicroservice.transfer;

import java.math.BigDecimal;

public interface TransferStrategy {

    void transfer(String fromAccount, String toAccount, BigDecimal amount);
}
