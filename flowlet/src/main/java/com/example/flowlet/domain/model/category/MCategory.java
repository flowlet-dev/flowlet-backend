package com.example.flowlet.domain.model.category;

import com.example.flowlet.domain.model.common.AbstractAuditableEntity;
import com.example.flowlet.domain.model.flow.FlowType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "m_category", schema = "flowlet")
public class MCategory extends AbstractAuditableEntity {
    @Id
    @Size(max = 20)
    @Column(name = "category_cd", nullable = false, length = 20)
    private String categoryCd;

    @Size(max = 50)
    @NotNull
    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "flow_type", nullable = false, length = 10)
    private FlowType flowType;


    protected MCategory() {
    }

    /**
     * コンストラクタ。
     *
     * @param categoryCd   カテゴリーコード
     * @param categoryName カテゴリー名
     * @param flowType     フロー種別
     */
    public MCategory(String categoryCd, String categoryName, FlowType flowType) {
        if (categoryCd == null || categoryCd.isBlank()) {
            throw new IllegalArgumentException("Category code must not be blank.");
        }
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("Category name must not be blank.");
        }
        if (flowType == null) {
            throw new IllegalArgumentException("Flow type must not be null.");
        }
        this.categoryCd = categoryCd;
        this.categoryName = categoryName;
        this.flowType = flowType;
    }


}