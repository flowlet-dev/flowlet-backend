package com.example.flowlet.application.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 取引明細リクエストDTO
 */
@Getter
@Setter
public class TransactionDetailRequest {
    /** 金額 */
    private BigDecimal amount;

    /** カテゴリーコード */
    private String categoryCd;

    /** 実口座ID */
    private Long physicalAccountId;

    /** 仮想口座ID */
    private Long virtualAccountId;
}
