package com.example.flowlet.domain.account;

import lombok.Getter;

/**
 * 実口座ドメインモチEE
 */
@Getter
public class PhysicalAccount {
    private final Integer id;
    private final String accountName;
    private final String accountType;

    public PhysicalAccount(Integer id, String accountName, String accountType) {
        if (accountName == null || accountName.isBlank()) throw new IllegalArgumentException("Account name is required.");
        if (accountType == null || accountType.isBlank()) throw new IllegalArgumentException("Account type is required.");

        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
    }

}
