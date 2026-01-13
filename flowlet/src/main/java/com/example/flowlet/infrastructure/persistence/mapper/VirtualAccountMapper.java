package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.infrastructure.persistence.entity.MVirtualAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 仮想口座のドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring")
public interface VirtualAccountMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity 仮想口座エンティティ
     * @return 仮想口座ドメインモデル
     */
    VirtualAccount toDomain(MVirtualAccount entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain 仮想口座ドメインモデル
     * @return 仮想口座エンティティ
     */
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MVirtualAccount toEntity(VirtualAccount domain);
}
