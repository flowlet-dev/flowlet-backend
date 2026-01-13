package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 取引のレスポンス用DTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionResponse(
        Long transactionId,
        LocalDate transactionDate,
        String description,
        List<TransactionDetailResponse> details
) {
    /**
     * 取引明細のレスポンス用DTO。
     */
    public record TransactionDetailResponse(
            Long detailId,
            BigDecimal amount,
            String categoryName,
            String physicalAccountName,
            String virtualAccountName
    ) {
    }

    /**
     * ドメインモデルからDTOに変換します。
     *
     * @param domain 取引ドメインモデル
     * @return 取引レスポンスDTO
     */
    public static TransactionResponse fromDomain(Transaction domain) {
        var details = domain.details().stream()
                .map(d -> new TransactionDetailResponse(
                        d.detailId(),
                        d.amount(),
                        d.category() != null ? d.category().categoryName() : null,
                        d.physicalAccount() != null ? d.physicalAccount().accountName() : null,
                        d.virtualAccount() != null ? d.virtualAccount().accountName() : null
                ))
                .toList();

        return new TransactionResponse(
                domain.transactionId(),
                domain.transactionDate(),
                domain.description(),
                details
        );
    }
}
