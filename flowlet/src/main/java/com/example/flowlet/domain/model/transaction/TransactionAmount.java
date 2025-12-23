package com.example.flowlet.domain.model.transaction;

/**
 * 取引金額を表す値オブジェクト。
 */
public record TransactionAmount(int value) {

    public TransactionAmount {
        if (value <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero.");
        }
    }
}
