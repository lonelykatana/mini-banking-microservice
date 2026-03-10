package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.TransactionReport;
import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.entity.Transaction;
import com.erick.minibankingmicroservice.enums.TransferType;
import com.erick.minibankingmicroservice.exception.ApiException;
import com.erick.minibankingmicroservice.kafka.TransactionEvent;
import com.erick.minibankingmicroservice.kafka.TransactionEventProducer;
import com.erick.minibankingmicroservice.repository.AccountRepository;
import com.erick.minibankingmicroservice.repository.TransactionRepository;
import com.erick.minibankingmicroservice.transfer.TransferFactory;
import com.erick.minibankingmicroservice.transfer.TransferStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;
    private final TransferFactory transferFactory;
    private final AccountCacheService accountCacheService;
    private final TransactionEventProducer transactionEventProducer;
    private final ObjectMapper om;
    private final ApplicationEventPublisher eventPublisher;
    private final ExcelReportService excelReportService;

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        try {
            Account account = accountRepo
                    .findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ApiException("Account not found"));

            account.setBalance(account.getBalance().add(amount));

            accountRepo.save(account);

            accountCacheService.saveBalance(
                    account.getAccountNumber(),
                    account.getBalance()
            );

            Transaction trx = new Transaction();
            trx.setSourceAccountNumber(accountNumber);
            trx.setType("DEPOSIT");
            trx.setAmount(amount);
            trx.setCreatedAt(LocalDateTime.now());

            transactionRepo.save(trx);

            TransactionEvent event = new TransactionEvent();
            event.setTargetAccountNumber(accountNumber);
            event.setType("DEPOSIT");
            event.setAmount(amount);
            event.setTimestamp(LocalDateTime.now());


//            transactionEventProducer.publish(om.writeValueAsString(event));
            eventPublisher.publishEvent(om.writeValueAsString(event));
        } catch (Exception e) {
            logger.error("errror ininii", e);
            throw new ApiException("error bro");
        }
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepo
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ApiException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepo.save(account);

        accountCacheService.saveBalance(
                account.getAccountNumber(),
                account.getBalance()
        );

        Transaction trx = new Transaction();
        trx.setSourceAccountNumber(accountNumber);
        trx.setType("WITHDRAW");
        trx.setAmount(amount);
        trx.setCreatedAt(LocalDateTime.now());

        transactionRepo.save(trx);

        TransactionEvent event = new TransactionEvent();
        event.setSourceAccountNumber(accountNumber);
        event.setType("WITHDRAW");
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        try {
            eventPublisher.publishEvent(om.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new ApiException("fail to map object to string");
        }
    }

    @Transactional
    @Override
    public void transfer(String fromAccount, String toAccount, BigDecimal amount,
                         TransferType type) {
        TransferStrategy strategy = transferFactory.getStrategy(type);
        strategy.transfer(fromAccount, toAccount, amount);
    }

    @Override
    public ByteArrayInputStream getTransactionForReport(String branchId, String transactionType,
                                                        String startDate, String endDate) {

        ByteArrayInputStream stream = null;
        try {


            List<TransactionReport> trxData;

            LocalDateTime start = null;
            LocalDateTime end = null;

            if (startDate != null && !startDate.isBlank()) {
                start = LocalDate.parse(startDate)
                        .atStartOfDay();
            }

            if (endDate != null && !endDate.isBlank()) {
                end = LocalDate.parse(endDate)
                        .atTime(LocalTime.MAX);
            }

            List<Object[]> rows =
                    transactionRepo.findTransactionsForReport(
                            branchId,
                            transactionType,
                            start,
                            end
                    );

            trxData = rows.stream()
                    .map(r -> new TransactionReport(
                            (String) r[0],
                            (String) r[1],
                            (String) r[2],
                            (BigDecimal) r[3],
                            (String) r[4],
                            (String) r[5],
                            ((Timestamp) r[6]).toLocalDateTime()
                    ))
                    .toList();

            stream = excelReportService.generateExcel(trxData);
        } catch (Exception e) {
            logger.info("excel gagal cause ", e);
        }

        return stream;
    }


}
