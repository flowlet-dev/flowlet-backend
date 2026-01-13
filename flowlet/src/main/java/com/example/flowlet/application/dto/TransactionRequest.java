package com.example.flowlet.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 取引の登録・更新用リクエストDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionRequest(
    @NotNull
    LocalDate transactionDate,

    @Size(max = 200)
    String description,

    @NotNull
    @Size(min = 1)
    @Valid
    List<TransactionDetailRequest> details
) {
    /**
     * 取引明細の登録・更新用リクエストDTO。
     */
    public record TransactionDetailRequest(
        @NotNull
        BigDecimal amount,

        @NotBlank
        String categoryCd,

        @NotNull
        Long physicalAccountId,

        @NotNull
        Long virtualAccountId
    ) {}
}
