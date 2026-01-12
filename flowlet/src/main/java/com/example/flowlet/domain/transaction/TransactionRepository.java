package com.example.flowlet.domain.transaction;

/**
 * Transaction Repository Interface (Domain Layer).
 */
public interface TransactionRepository {
    void save(Transaction transaction);
}
