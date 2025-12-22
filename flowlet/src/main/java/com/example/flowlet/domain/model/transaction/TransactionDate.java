package com.example.flowlet.domain.model.transaction;

import java.time.LocalDate;

/**
 * 取引日を表す値オブジェクト。
 *
 * <p>
 * 取引日は金銭の増減が発生した日を表し、
 * 業務上、未来日を許容しない。
 * </p>
 */
public record TransactionDate(LocalDate value) {

    /**
     * コンストラクタ。
     *
     * @throws IllegalArgumentException 不正な取引日の場合
     */
    public TransactionDate {
        if (value == null) {
            throw new IllegalArgumentException("Transaction date must not be null.");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Transaction date must not be in the future.");
        }
    }
}
