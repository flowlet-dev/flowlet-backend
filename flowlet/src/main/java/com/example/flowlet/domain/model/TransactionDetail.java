package com.example.flowlet.domain.model;

import java.math.BigDecimal;

/**
 * 取引明細を表すドメインモデル。
 *
 * @param detailId 明細ID
 * @param amount 金額
 * @param category カテゴリー
 * @param physicalAccount 実口座
 * @param virtualAccount 仮想口座
 */
public record TransactionDetail(
    Long detailId,
    BigDecimal amount,
    Category category,
    PhysicalAccount physicalAccount,
    VirtualAccount virtualAccount
) {}
