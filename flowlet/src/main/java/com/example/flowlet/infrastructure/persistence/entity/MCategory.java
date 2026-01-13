package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_category")
@Getter
@Setter
public class MCategory extends BaseEntity {

    @Id
    @Column(name = "category_cd", length = 20)
    private String categoryCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_cd")
    private MCategory parentCategory;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "flow_type", nullable = false, length = 10)
    private String flowType;
}
