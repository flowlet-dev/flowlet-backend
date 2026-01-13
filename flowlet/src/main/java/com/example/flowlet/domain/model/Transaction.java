package com.example.flowlet.domain.model;

import java.time.LocalDate;
import java.util.List;

/**
 * 取引を表すドメインモデル。
 *
 * @param transactionId 取引ID
 * @param transactionDate 取引日
 * @param description 摘要
 * @param details 取引明細リスト
 */
public record Transaction(
    Long transactionId,
    LocalDate transactionDate,
    String description,
    List<TransactionDetail> details
) {}
