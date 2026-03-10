package com.erick.minibankingmicroservice.transfer;

import com.erick.minibankingmicroservice.client.MasterDataClient;
import com.erick.minibankingmicroservice.entity.Account;
import com.erick.minibankingmicroservice.entity.Transaction;
import com.erick.minibankingmicroservice.exception.ApiException;
import com.erick.minibankingmicroservice.kafka.TransactionEvent;
import com.erick.minibankingmicroservice.kafka.TransactionEventProducer;
import com.erick.minibankingmicroservice.repository.AccountRepository;
import com.erick.minibankingmicroservice.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
public class RtgsTransfer implements TransferStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RtgsTransfer.class);
    private final AccountRepository accRepo;
    private final TransactionRepository trxRepo;
    private final TransactionEventProducer transactionEventProducer;
    private final ObjectMapper om;
    private final ApplicationEventPublisher eventPublisher;
    private final MasterDataClient masterDataClient;

    @Transactional
    @Override
    public void transfer(String fromAccount, String toAccount, BigDecimal amount) {

        logger.info("Processing RTGS transfer");

        BigDecimal fee = masterDataClient.getFee("RTGS_FEE", "TRANSFER");

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

        Transaction trx = new Transaction();
        trx.setSourceAccountNumber(fromAccount);
        trx.setTargetAccountNumber(toAccount);
        trx.setType("RTGS_TRANSFER");
        trx.setAmount(amount);
        trx.setCreatedAt(LocalDateTime.now());

        trxRepo.save(trx);

        TransactionEvent event = new TransactionEvent();
        event.setSourceAccountNumber(sender.getAccountNumber());
        event.setTargetAccountNumber(receiver.getAccountNumber());
        event.setType("RTGS_TRANSFER");
        event.setAmount(amount);
        event.setTimestamp(LocalDateTime.now());

        try {
//            transactionEventProducer.publish(om.writeValueAsString(event));
            eventPublisher.publishEvent(om.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new ApiException("fail to map object to string");
        }
    }
}
