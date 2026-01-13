package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "t_transaction_detail")
@Getter
@Setter
public class TTransactionDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

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
}
