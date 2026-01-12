package com.example.flowlet.domain.category;

import com.example.flowlet.domain.common.FlowType;
import lombok.Getter;

/**
 * カテゴリードメインモデル。
 */
@Getter
public class Category {
    private final String categoryCd;
    private final String categoryName;
    private final FlowType flowType;
    private final Category parentCategory;

    public Category(String categoryCd, String categoryName, FlowType flowType, Category parentCategory) {
        if (categoryCd == null || categoryCd.isBlank()) {
            throw new IllegalArgumentException("Category code must not be blank.");
        }
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("Category name must not be blank.");
        }
        if (flowType == null) {
            throw new IllegalArgumentException("Flow type must not be null.");
        }
        if (parentCategory != null && parentCategory.getFlowType() != flowType) {
            throw new IllegalArgumentException("Parent category flow type must match.");
        }

        this.categoryCd = categoryCd;
        this.categoryName = categoryName;
        this.flowType = flowType;
        this.parentCategory = parentCategory;
    }

}
