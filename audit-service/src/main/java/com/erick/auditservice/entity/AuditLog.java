package com.erick.auditservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "audit_log")
public class AuditLog {

    @Id
    private String id;
    private String accountNumber;
    private String targetAccountNumber;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
