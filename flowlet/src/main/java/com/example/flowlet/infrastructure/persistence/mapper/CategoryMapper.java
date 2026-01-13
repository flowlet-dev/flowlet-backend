package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.Category;
import com.example.flowlet.infrastructure.persistence.entity.MCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * カテゴリーのドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity カテゴリーエンティティ
     * @return カテゴリードメインモデル
     */
    Category toDomain(MCategory entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain カテゴリードメインモデル
     * @return カテゴリーエンティティ
     */
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MCategory toEntity(Category domain);
}
