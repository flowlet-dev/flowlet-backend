package com.example.flowlet.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 財務サマリー（今月あといくら使えるか等）のレスポンスDTO。
 */
public record FinancialSummaryResponse(
    LocalDate startDate,
    LocalDate endDate,
    BigDecimal currentLiquidAssets,
    BigDecimal periodBalance,
    BigDecimal availableAmount
) {}
