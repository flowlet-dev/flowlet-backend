package com.example.flowlet.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 予算登録・更新リクエストDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BudgetRequest(
    @NotNull String categoryCd,
    @NotNull @Positive BigDecimal amount,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate
) {}
