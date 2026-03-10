package com.erick.auditservice.event;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionEvent {

    private String sourceAccountNumber;
    private String targetAccountNumber;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
