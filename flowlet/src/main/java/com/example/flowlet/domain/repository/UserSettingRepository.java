package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.UserSetting;
import java.util.Optional;

/**
 * ユーザー設定のリポジトリインターフェース。
 */
public interface UserSettingRepository {
    Optional<UserSetting> findByKey(String settingKey);
    UserSetting save(UserSetting userSetting);
}
