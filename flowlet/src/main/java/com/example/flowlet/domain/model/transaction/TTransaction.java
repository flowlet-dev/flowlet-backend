package com.example.flowlet.domain.model.transaction;

import com.example.flowlet.domain.model.category.MCategory;
import com.example.flowlet.domain.model.common.AbstractAuditableEntity;
import com.example.flowlet.domain.model.flow.FlowType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "t_transaction", schema = "flowlet")
public class TTransaction extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_no", nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount", nullable = false))
    private TransactionAmount amount;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "transaction_date", nullable = false))
    private TransactionDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "flow_type", nullable = false, length = 20)
    private FlowType flowType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_cd", nullable = false)
    private MCategory category;

    protected TTransaction() {
    }

    /**
     * コンストラクタ。
     *
     * @param amount          取引金額
     * @param transactionDate 取引日
     * @param flowType        フロー種別
     * @param category        カテゴリー
     */
    public TTransaction(
            TransactionAmount amount,
            TransactionDate transactionDate,
            FlowType flowType,
            MCategory category
    ) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null.");
        }
        if (transactionDate == null) {
            throw new IllegalArgumentException("Transaction date must not be null.");
        }
        if (flowType == null) {
            throw new IllegalArgumentException("Flow type must not be null.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null.");
        }
        if (category.getFlowType() != flowType) {
            throw new IllegalArgumentException("Category flow type must match transaction flow type.");
        }

        this.amount = amount;
        this.transactionDate = transactionDate;
        this.flowType = flowType;
        this.category = category;
    }


}