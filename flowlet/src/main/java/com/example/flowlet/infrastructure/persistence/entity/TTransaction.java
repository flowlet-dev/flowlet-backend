package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_transaction", schema = "flowlet")
public class TTransaction extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TTransactionDetail> details = new ArrayList<>();

    public TTransaction() {
    }

    /**
     * コンストラクタ。
     *
     * @param transactionDate 取引日
     * @param description     摘要
     */
    public TTransaction(LocalDate transactionDate, String description) {
        if (transactionDate == null) {
            throw new IllegalArgumentException("Transaction date must not be null.");
        }
        this.transactionDate = transactionDate;
        this.description = description;
    }

    /**
     * 譏守ｴE繧定ｿE蜉縺吶E縲・
     * @param detail 譏守ｴE
     */
    public void addDetail(TTransactionDetail detail) {
        this.details.add(detail);
        detail.setTransaction(this);
    }
}
