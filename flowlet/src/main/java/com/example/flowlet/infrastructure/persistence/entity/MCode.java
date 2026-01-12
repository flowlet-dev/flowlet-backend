package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 汎用コードEスタE
 * シスチE冁EE吁E区刁EEEypeEE表示名や並び頁E管琁EるE
 */
@Getter
@Setter
@Entity
@Table(name = "m_code", schema = "flowlet")
@IdClass(MCodeId.class)
public class MCode extends AbstractAuditableEntity {

    @Id
    @Column(name = "code_group", nullable = false, length = 30)
    private String codeGroup;

    @Id
    @Column(name = "code_value", nullable = false, length = 30)
    private String codeValue;

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public MCode() {
    }

    public MCode(String codeGroup, String codeValue, String displayName, Integer sortOrder) {
        if (codeGroup == null || codeGroup.isBlank()) throw new IllegalArgumentException("Code group is required.");
        if (codeValue == null || codeValue.isBlank()) throw new IllegalArgumentException("Code value is required.");
        if (displayName == null || displayName.isBlank()) throw new IllegalArgumentException("Display name is required.");
        if (sortOrder == null) throw new IllegalArgumentException("Sort order is required.");

        this.codeGroup = codeGroup;
        this.codeValue = codeValue;
        this.displayName = displayName;
        this.sortOrder = sortOrder;
    }
}
