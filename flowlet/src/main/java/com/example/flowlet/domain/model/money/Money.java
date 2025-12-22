package com.example.flowlet.domain.model.money;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 金額を表す値オブジェクト。
 *
 * <p>
 * 本クラスは不変であり、
 * 金額に関する業務ルールを内包する。
 * </p>
 */
public record Money(BigDecimal amount) {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    /**
     * コンストラクタ。
     *
     * <p>
     * 金額は0以上でなければならない。
     * </p>
     *
     * @throws IllegalArgumentException 不正な金額の場合
     */
    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null.");
        }
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be zero or positive.");
        }
    }

    /**
     * 金額を加算する。
     *
     * @param other 加算対象
     * @return 加算後の金額
     */
    public Money add(Money other) {
        Objects.requireNonNull(other, "Other money must not be null.");
        return new Money(this.amount.add(other.amount));
    }

    /**
     * 金額を減算する。
     *
     * @param other 減算対象
     * @return 減算後の金額
     * @throws IllegalArgumentException 減算結果が負になる場合
     */
    public Money subtract(Money other) {
        Objects.requireNonNull(other, "Other money must not be null.");
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Resulting amount must not be negative.");
        }
        return new Money(result);
    }
}
