package com.erick.minibankingmicroservice.util;

import java.util.concurrent.atomic.AtomicLong;

public class AccountNumberGenerator {

    private static final AtomicLong sequence = new AtomicLong(1);

    // can be from db sequence or other service, keep simple use this
    public static String generate(String branchId) {

        long next = sequence.getAndIncrement();

        return branchId + String.format("%08d", next);
    }
}
