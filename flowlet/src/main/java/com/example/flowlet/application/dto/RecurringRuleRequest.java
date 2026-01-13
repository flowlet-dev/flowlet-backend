package com.example.flowlet.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 定期実行ルールの登録・更新リクエストDTO。
 */
public record RecurringRuleRequest(
    @NotBlank String ruleName,
    @NotNull BigDecimal amount,
    @NotNull @Min(1) @Max(31) Integer transactionDay,
    @NotBlank String categoryCd,
    @NotNull Long physicalAccountId,
    @NotNull Long virtualAccountId,
    String description,
    boolean isActive
) {}
