package com.example.flowlet.domain.model;

/**
 * ユーザー設定を表すドメインモデル。
 *
 * @param settingKey 設定キー
 * @param settingValue 設定値
 */
public record UserSetting(
    String settingKey,
    String settingValue
) {}
