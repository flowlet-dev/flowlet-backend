package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 取引E細E
 * スプリチE入力を実現するためのE体的な金額カチEリー口座の絁E合わせE
 */
@Getter
@Setter
@Entity
@Table(name = "t_transaction_detail", schema = "flowlet")
public class TTransactionDetail extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TTransaction transaction;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_cd", nullable = false)
    private MCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_account_id", nullable = false)
    private MPhysicalAccount physicalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_account_id", nullable = false)
    private MVirtualAccount virtualAccount;

    public TTransactionDetail() {
    }

}
