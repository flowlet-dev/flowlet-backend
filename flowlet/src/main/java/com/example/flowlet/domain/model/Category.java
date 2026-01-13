package com.example.flowlet.domain.model;

/**
 * カテゴリーを表すドメインモデル。
 *
 * @param categoryCd カテゴリーコード
 * @param parentCategory 親カテゴリー
 * @param categoryName カテゴリー名
 * @param flowType 収支区分 (INCOME/OUTGO)
 */
public record Category(
    String categoryCd,
    Category parentCategory,
    String categoryName,
    String flowType
) {}
