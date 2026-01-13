package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_physical_account")
@Getter
@Setter
public class MPhysicalAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "physical_account_id")
    private Long physicalAccountId;

    @Column(name = "account_name", nullable = false, length = 50)
    private String accountName;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;
}
