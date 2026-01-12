package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 仮想口座E
 * 旅行用予備費などお金E使用目皁E管琁EるE
 */
@Getter
@Setter
@Entity
@Table(name = "m_virtual_account", schema = "flowlet")
public class MVirtualAccount extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "virtual_account_id", nullable = false)
    private Integer id;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "is_liquid", nullable = false)
    private Boolean isLiquid;

    public MVirtualAccount() {
    }

    public MVirtualAccount(String accountName, Boolean isLiquid) {
        if (accountName == null || accountName.isBlank()) throw new IllegalArgumentException("Account name is required.");
        if (isLiquid == null) throw new IllegalArgumentException("isLiquid flag is required.");

        this.accountName = accountName;
        this.isLiquid = isLiquid;
    }
}
