package com.example.flowlet.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 財務サマリー（今月あといくら使えるか等）のレスポンスDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FinancialSummaryResponse(
    LocalDate startDate,
    LocalDate endDate,
    BigDecimal currentLiquidAssets,
    BigDecimal periodBalance,
    BigDecimal availableAmount
) {}
