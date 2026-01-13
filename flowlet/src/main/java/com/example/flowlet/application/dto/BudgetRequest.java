package com.example.flowlet.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 予算登録・更新リクエストDTO。
 */
public record BudgetRequest(
    @NotNull String categoryCd,
    @NotNull @Positive BigDecimal amount,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate
) {}
