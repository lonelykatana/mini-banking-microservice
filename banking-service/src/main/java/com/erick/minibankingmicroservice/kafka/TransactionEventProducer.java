package com.erick.minibankingmicroservice.kafka;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class TransactionEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(String message) {
        kafkaTemplate.send(
                KafkaTopics.TRANSACTION_TOPIC,
                message
        );
    }
}
