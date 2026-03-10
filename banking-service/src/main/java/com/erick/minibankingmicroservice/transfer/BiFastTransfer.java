package com.erick.minibankingmicroservice.transfer;

import com.erick.minibankingmicroservice.client.MasterDataClient;
import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.entity.Transaction;
import com.erick.minibankingmicroservice.exception.ApiException;
import com.erick.minibankingmicroservice.kafka.TransactionEvent;
import com.erick.minibankingmicroservice.kafka.TransactionEventProducer;
import com.erick.minibankingmicroservice.repository.AccountRepository;
import com.erick.minibankingmicroservice.repository.TransactionRepository;
import com.erick.minibankingmicroservice.service.AccountCacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BiFastTransfer implements TransferStrategy {

    private final AccountRepository accRepo;
    private final TransactionRepository trxRepo;
    private final AccountCacheService accountCacheService;
    private final TransactionEventProducer transactionEventProducer;
    private final ObjectMapper om;
    private final ApplicationEventPublisher eventPublisher;
    private final MasterDataClient masterDataClient;

    private static final Logger logger = LoggerFactory.getLogger(BiFastTransfer.class);

    @Override
    public void transfer(String fromAccount, String toAccount, BigDecimal amount) {

        logger.info("Processing BI-FAST transfer");

        // Dummy logic

        BigDecimal fee = masterDataClient.getFee("BIFAST_FEE", "TRANSFER");

        Account sender = accRepo
                .findByAccountNumber(fromAccount)
                .orElseThrow();

        Account receiver = accRepo
                .findByAccountNumber(toAccount)
                .orElseThrow();

        sender.setBalance(sender.getBalance().subtract(amount.add(fee)));
        if (sender.getBalance().compareTo(amount.add(fee)) < 0) {
            throw new ApiException("Insufficient balance");
        }
        receiver.setBalance(receiver.getBalance().add(amount));

        accRepo.save(sender);
        accRepo.save(receiver);

        accountCacheService.saveBalance(
                sender.getAccountNumber(),
                sender.getBalance()
        );

        accountCacheService.saveBalance(
                receiver.getAccountNumber(),
                receiver.getBalance()
        );

        Transaction trx = new Transaction();
        trx.setSourceAccountNumber(fromAccount);
        trx.setTargetAccountNumber(toAccount);
        trx.setType("BI_FAST_TRANSFER");
        trx.setAmount(amount);
        trx.setCreatedAt(LocalDateTime.now());

        trxRepo.save(trx);

        TransactionEvent event = new TransactionEvent();
        event.setSourceAccountNumber(sender.getAccountNumber());
        event.setTargetAccountNumber(receiver.getAccountNumber());
        event.setType("BI_FAST_TRANSFER");
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        try {
            eventPublisher.publishEvent(om.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new ApiException("fail to map object to string");
        }
    }
}
