package com.example.flowlet.domain.transaction;

import com.example.flowlet.domain.category.Category;
import com.example.flowlet.domain.account.PhysicalAccount;
import com.example.flowlet.domain.account.VirtualAccount;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * TransactionDetail Domain Model.
 */
@Getter
public class TransactionDetail {
    private final Long id;
    private final BigDecimal amount;
    private final Category category;
    private final PhysicalAccount physicalAccount;
    private final VirtualAccount virtualAccount;

    public TransactionDetail(Long id, BigDecimal amount, Category category, PhysicalAccount physicalAccount, VirtualAccount virtualAccount) {
        if (amount == null) throw new IllegalArgumentException("Amount is required.");
        if (category == null) throw new IllegalArgumentException("Category is required.");
        if (physicalAccount == null) throw new IllegalArgumentException("Physical account is required.");
        if (virtualAccount == null) throw new IllegalArgumentException("Virtual account is required.");

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.physicalAccount = physicalAccount;
        this.virtualAccount = virtualAccount;
    }

}
