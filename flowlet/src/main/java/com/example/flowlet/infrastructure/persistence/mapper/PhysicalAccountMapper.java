package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.infrastructure.persistence.entity.MPhysicalAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 実口座のドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring")
public interface PhysicalAccountMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity 実口座エンティティ
     * @return 実口座ドメインモデル
     */
    PhysicalAccount toDomain(MPhysicalAccount entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain 実口座ドメインモデル
     * @return 実口座エンティティ
     */
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MPhysicalAccount toEntity(PhysicalAccount domain);
}
