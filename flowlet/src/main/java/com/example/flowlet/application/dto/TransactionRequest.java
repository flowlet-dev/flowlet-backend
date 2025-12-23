package com.example.flowlet.application.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * 取引登録リクエストDTO。
 */
@Getter
public class TransactionRequest {

    /** 金額 */
    private int amount;

    /** 取引日 */
    private LocalDate transactionDate;

    /** フロー種別 */
    private String flowType;

    /** カテゴリーコード */
    private String categoryCd;

}
