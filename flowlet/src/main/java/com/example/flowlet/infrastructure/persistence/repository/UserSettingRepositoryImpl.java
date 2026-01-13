package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.UserSetting;
import com.example.flowlet.domain.repository.UserSettingRepository;
import com.example.flowlet.infrastructure.persistence.entity.MUserSetting;
import com.example.flowlet.infrastructure.persistence.mapper.UserSettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class UserSettingRepositoryImpl implements UserSettingRepository {

    private final JpaUserSettingRepository jpaRepository;
    private final UserSettingMapper mapper;

    @Override
    public Optional<UserSetting> findByKey(String settingKey) {
        return jpaRepository.findById(settingKey).map(mapper::toDomain);
    }

    @Override
    public UserSetting save(UserSetting userSetting) {
        MUserSetting entity = mapper.toEntity(userSetting);
        MUserSetting savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
