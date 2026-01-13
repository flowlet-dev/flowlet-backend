package com.example.flowlet.domain.model;

import java.math.BigDecimal;

/**
 * 定期実行ルールを表すドメインモデル。
 *
 * @param recurringRuleId ルールID
 * @param ruleName ルール名
 * @param amount 金額
 * @param transactionDay 実行日（毎月）
 * @param category カテゴリー
 * @param physicalAccount 実口座
 * @param virtualAccount 仮想口座
 * @param description 摘要
 * @param isActive 有効フラグ
 */
public record RecurringRule(
    Long recurringRuleId,
    String ruleName,
    BigDecimal amount,
    Integer transactionDay,
    Category category,
    PhysicalAccount physicalAccount,
    VirtualAccount virtualAccount,
    String description,
    boolean isActive
) {}
