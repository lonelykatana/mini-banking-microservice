package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.TransactionReport;
import com.erick.minibankingmicroservice.enums.TransferType;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void deposit(String accountNumber, BigDecimal amount);

    void withdraw(String accountNumber, BigDecimal amount);

    void transfer(String fromAccount, String toAccount, BigDecimal amount, TransferType type);

    ByteArrayInputStream getTransactionForReport(String branchId, String transactionType,
                                                 String startDate, String endDate);

}
