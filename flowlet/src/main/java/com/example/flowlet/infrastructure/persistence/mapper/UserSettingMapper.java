package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.UserSetting;
import com.example.flowlet.infrastructure.persistence.entity.MUserSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ユーザー設定のドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring")
public interface UserSettingMapper {

    UserSetting toDomain(MUserSetting entity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MUserSetting toEntity(UserSetting domain);
}
