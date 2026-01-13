package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.Code;
import com.example.flowlet.infrastructure.persistence.entity.MCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 汎用コードのドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring")
public interface CodeMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity 汎用コードエンティティ
     * @return 汎用コードドメインモデル
     */
    @Mapping(target = "codeGroup", source = "id.codeGroup")
    @Mapping(target = "codeValue", source = "id.codeValue")
    Code toDomain(MCode entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain 汎用コードドメインモデル
     * @return 汎用コードエンティティ
     */
    @Mapping(target = "id.codeGroup", source = "codeGroup")
    @Mapping(target = "id.codeValue", source = "codeValue")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MCode toEntity(Code domain);
}
