package com.erick.auditservice.kafka;

import com.erick.auditservice.entity.AuditLog;
import com.erick.auditservice.event.TransactionEvent;
import com.erick.auditservice.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final AuditRepository auditRepo;
    private final ObjectMapper om;

    private static final Logger logger = LoggerFactory.getLogger(TransactionEventConsumer.class);

    @KafkaListener(topics = "banking.transactions")
    public void consume(String message) {
        TransactionEvent event;
        try {
            event = om.readValue(message, TransactionEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        AuditLog log = new AuditLog();
        log.setAccountNumber(event.getSourceAccountNumber() == null ? null :
                event.getSourceAccountNumber());
        log.setTargetAccountNumber(event.getTargetAccountNumber());
        log.setType(event.getType());
        log.setAmount(event.getAmount());
        log.setTimestamp(event.getTimestamp());

        auditRepo.save(log);

        logger.info("Audit log success: " + event);
    }
}
