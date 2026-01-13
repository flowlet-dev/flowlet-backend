package com.example.flowlet.domain.model;

/**
 * 仮想口座を表すドメインモデル。
 *
 * @param virtualAccountId 仮想口座ID
 * @param accountName 目的名
 * @param isLiquid 流動資産フラグ
 */
public record VirtualAccount(
    Long virtualAccountId,
    String accountName,
    Boolean isLiquid
) {}
