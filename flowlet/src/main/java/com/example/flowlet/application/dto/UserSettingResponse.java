package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.UserSetting;

/**
 * ユーザー設定のレスポンスDTO。
 */
public record UserSettingResponse(
    String settingKey,
    String settingValue
) {
    public static UserSettingResponse fromDomain(UserSetting userSetting) {
        return new UserSettingResponse(userSetting.settingKey(), userSetting.settingValue());
    }
}
