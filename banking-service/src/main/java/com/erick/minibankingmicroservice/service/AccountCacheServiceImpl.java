package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCacheServiceImpl implements AccountCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "account_balance:";

    @Override
    public BigDecimal getBalance(String accountNumber) {
        Object value = redisTemplate.opsForValue().get(PREFIX + accountNumber);

        if(value != null){
            return new BigDecimal(value.toString());
        }

        return null;
    }

    @Override
    public void saveBalance(String accountNumber, BigDecimal balance) {
        redisTemplate.opsForValue()
                .set(PREFIX + accountNumber, balance, Duration.ofMinutes(10));
    }

    @Override
    public void evictBalance(String accountNumber) {
        redisTemplate.delete(PREFIX + accountNumber);
    }
}
