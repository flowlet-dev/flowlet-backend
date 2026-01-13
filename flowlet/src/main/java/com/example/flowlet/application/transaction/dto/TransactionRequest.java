package com.example.flowlet.application.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * 取引登録リクエストDTO
 */
@Getter
@Setter
public class TransactionRequest {

    /** 取引日 */
    private LocalDate transactionDate;

    /** 摘要（全体の説明） */
    private String description;

    /** 取引明細リスト（スプリット対応） */
    private List<TransactionDetailRequest> details;

}
