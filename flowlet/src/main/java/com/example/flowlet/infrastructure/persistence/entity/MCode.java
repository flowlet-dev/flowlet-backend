package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_code")
@Getter
@Setter
public class MCode extends BaseEntity {

    @EmbeddedId
    private CodeId id;

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}
