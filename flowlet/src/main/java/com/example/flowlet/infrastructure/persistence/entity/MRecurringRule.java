package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "m_recurring_rule")
@Getter
@Setter
public class MRecurringRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recurring_rule_id")
    private Long recurringRuleId;

    @Column(name = "rule_name", nullable = false, length = 100)
    private String ruleName;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_day", nullable = false)
    private Integer transactionDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_cd", nullable = false)
    private MCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_account_id", nullable = false)
    private MPhysicalAccount physicalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_account_id", nullable = false)
    private MVirtualAccount virtualAccount;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
