package com.example.flowlet.domain.model;

/**
 * 汎用コードを表すドメインモデル。
 *
 * @param codeGroup コードグループ
 * @param codeValue コード値
 * @param displayName 表示名
 * @param sortOrder 並び順
 */
public record Code(
    String codeGroup,
    String codeValue,
    String displayName,
    Integer sortOrder
) {}
