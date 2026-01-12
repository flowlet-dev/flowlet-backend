package com.example.flowlet.domain.transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Transaction Domain Model (Parent).
 */
public record Transaction(Long id, LocalDate transactionDate, String description, List<TransactionDetail> details) {
    public Transaction(Long id, LocalDate transactionDate, String description, List<TransactionDetail> details) {
        if (transactionDate == null) throw new IllegalArgumentException("Transaction date is required.");
        this.id = id;
        this.transactionDate = transactionDate;
        this.description = description;
        this.details = new ArrayList<>(details != null ? details : Collections.emptyList());
    }

    @Override
    public List<TransactionDetail> details() {
        return Collections.unmodifiableList(details);
    }

}
