package com.erick.minibankingmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionReport {

    private String sourceAccountNumber;
    private String targetAccountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String branchId;
    private String branchName;
    private LocalDateTime createdAt;
}
