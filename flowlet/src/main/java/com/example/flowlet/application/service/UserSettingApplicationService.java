package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.UserSettingRequest;
import com.example.flowlet.application.dto.UserSettingResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.UserSetting;
import com.example.flowlet.domain.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザー設定に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSettingApplicationService {

    private final UserSettingRepository userSettingRepository;

    public UserSettingResponse findByKey(String key) {
        return userSettingRepository.findByKey(key)
                .map(UserSettingResponse::fromDomain)
                .orElseThrow(() -> new BusinessException("error.resource.not_found", "ユーザー設定", key));
    }

    @Transactional
    public UserSettingResponse update(String key, UserSettingRequest request) {
        UserSetting setting = new UserSetting(key, request.settingValue());
        UserSetting saved = userSettingRepository.save(setting);
        return UserSettingResponse.fromDomain(saved);
    }
}
