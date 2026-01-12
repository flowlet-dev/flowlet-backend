package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 実口座E
 * 銀行証券クレジチEカード現金など物琁Eなおの場所を管琁EるE
 */
@Getter
@Setter
@Entity
@Table(name = "m_physical_account", schema = "flowlet")
public class MPhysicalAccount extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "physical_account_id", nullable = false)
    private Integer id;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    public MPhysicalAccount() {
    }

    public MPhysicalAccount(String accountName, String accountType) {
        if (accountName == null || accountName.isBlank()) throw new IllegalArgumentException("Account name is required.");
        if (accountType == null || accountType.isBlank()) throw new IllegalArgumentException("Account type is required.");

        this.accountName = accountName;
        this.accountType = accountType;
    }
}
