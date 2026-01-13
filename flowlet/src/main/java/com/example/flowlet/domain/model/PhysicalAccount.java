package com.example.flowlet.domain.model;

/**
 * 実口座を表すドメインモデル。
 *
 * @param physicalAccountId 実口座ID
 * @param accountName 口座名
 * @param accountType 口座種別 (BANK, CARD, etc)
 */
public record PhysicalAccount(
    Long physicalAccountId,
    String accountName,
    String accountType
) {}
