package com.example.flowlet.domain.account;

/**
 * 仮想口座ドメインモチEE
 */
public record VirtualAccount(Integer id, String accountName, Boolean isLiquid) {
    public VirtualAccount {
        if (accountName == null || accountName.isBlank())
            throw new IllegalArgumentException("Account name is required.");
        if (isLiquid == null) throw new IllegalArgumentException("isLiquid flag is required.");

    }

}
