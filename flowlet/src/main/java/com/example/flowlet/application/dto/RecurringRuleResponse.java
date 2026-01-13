package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.RecurringRule;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * 定期実行ルールのレスポンスDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecurringRuleResponse(
    Long recurringRuleId,
    String ruleName,
    BigDecimal amount,
    Integer transactionDay,
    String categoryCd,
    String categoryName,
    Long physicalAccountId,
    String physicalAccountName,
    Long virtualAccountId,
    String virtualAccountName,
    String description,
    boolean isActive
) {
    public static RecurringRuleResponse fromDomain(RecurringRule domain) {
        return new RecurringRuleResponse(
            domain.recurringRuleId(),
            domain.ruleName(),
            domain.amount(),
            domain.transactionDay(),
            domain.category().categoryCd(),
            domain.category().categoryName(),
            domain.physicalAccount().physicalAccountId(),
            domain.physicalAccount().accountName(),
            domain.virtualAccount().virtualAccountId(),
            domain.virtualAccount().accountName(),
            domain.description(),
            domain.isActive()
        );
    }
}
