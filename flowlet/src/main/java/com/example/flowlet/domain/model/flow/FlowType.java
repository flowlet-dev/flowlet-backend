package com.example.flowlet.domain.model.flow;

/**
 * フロー種別を表す列挙型。
 *
 * <p>
 * フロー種別は、取引やカテゴリーが
 * 「収入」か「支出」かを表す業務上の概念である。
 * </p>
 *
 * <p>
 * Category と Transaction の両方で共通して使用される
 * ユビキタス言語として定義する。
 * </p>
 */
public enum FlowType {

    /**
     * 収入
     */
    INCOME,

    /**
     * 支出
     */
    EXPENSE;

    /**
     * 収入かどうかを判定する。
     *
     * @return 収入の場合 true
     */
    public boolean isIncome() {
        return this == INCOME;
    }

    /**
     * 支出かどうかを判定する。
     *
     * @return 支出の場合 true
     */
    public boolean isExpense() {
        return this == EXPENSE;
    }
}
