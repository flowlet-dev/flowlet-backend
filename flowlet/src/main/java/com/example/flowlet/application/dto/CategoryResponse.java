package com.example.flowlet.application.dto;

import java.util.List;

/**
 * カテゴリーレスポンスDTO。
 */
public record CategoryResponse(
        String categoryCd,
        String categoryName,
        String parentCategoryCd,
        String flowType, // 収支区分など
        List<CategoryResponse> childrenCategories
) {}
