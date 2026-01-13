package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.Budget;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 予算レスポンスDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BudgetResponse(
    Long budgetId,
    String categoryCd,
    String categoryName,
    BigDecimal amount,
    LocalDate startDate,
    LocalDate endDate
) {
    public static BudgetResponse fromDomain(Budget domain) {
        return new BudgetResponse(
            domain.budgetId(),
            domain.category().categoryCd(),
            domain.category().categoryName(),
            domain.amount(),
            domain.startDate(),
            domain.endDate()
        );
    }
}
