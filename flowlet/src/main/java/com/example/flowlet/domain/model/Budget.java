package com.example.flowlet.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 予算を表すドメインモデル。
 *
 * @param budgetId 予算ID
 * @param category カテゴリー
 * @param amount 予算額
 * @param startDate 開始日
 * @param endDate 終了日
 */
public record Budget(
    Long budgetId,
    Category category,
    BigDecimal amount,
    LocalDate startDate,
    LocalDate endDate
) {}
