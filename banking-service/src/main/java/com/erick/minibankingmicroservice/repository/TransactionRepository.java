package com.erick.minibankingmicroservice.repository;

import com.erick.minibankingmicroservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findBySourceAccountNumber(String sourceAccNumber);

    @Query(value = """
            SELECT source_account_number,
                   COUNT(*) AS total_transactions,
                   SUM(amount) AS total_amount
            FROM transactions
            GROUP BY source_account_number
            ORDER BY total_amount DESC
            LIMIT 10
            """, nativeQuery = true)
    List<Object[]> getTopAccountsByTransactionVolume();

    @Query(value = """
            SELECT 
                t.source_acc_number,
                t.target_account_number,
                t.type,
                t.amount,
                a.branch_id,
                a.branch_name,
                t.created_at
            FROM transactions t
            JOIN accounts a ON t.source_acc_number = a.account_number
            WHERE 
                (:branchId IS NULL OR a.branch_id = :branchId)
            AND (:transactionType IS NULL OR t.type = :transactionType)
            AND (t.created_at >= COALESCE(:startDate, t.created_at))
            AND (t.created_at <= COALESCE(:endDate, t.created_at))
            ORDER BY t.created_at DESC
            """, nativeQuery = true)
    List<Object[]> findTransactionsForReport(
            String branchId,
            String transactionType,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}
