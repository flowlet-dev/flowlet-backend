package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_virtual_account")
@Getter
@Setter
public class MVirtualAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "virtual_account_id")
    private Long virtualAccountId;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "is_liquid", nullable = false)
    private Boolean isLiquid;
}
