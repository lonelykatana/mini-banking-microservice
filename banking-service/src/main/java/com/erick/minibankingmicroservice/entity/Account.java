package com.erick.minibankingmicroservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Version
    @Column(nullable = false)
    private Long version;
}
