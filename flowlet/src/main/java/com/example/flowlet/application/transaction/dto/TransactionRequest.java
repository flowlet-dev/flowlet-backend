package com.example.flowlet.application.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 取引登録リクエスチETOE
 */
@Getter
@Setter
public class TransactionRequest {

    /** 金顁E*/
    private int amount;

    /** 取引日 */
    private LocalDate transactionDate;

    /** フロー種別 */
    private String flowType;

    /** カチEリーコーチE*/
    private String categoryCd;

}
