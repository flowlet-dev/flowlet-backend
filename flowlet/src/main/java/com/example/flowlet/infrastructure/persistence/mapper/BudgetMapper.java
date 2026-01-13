package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.infrastructure.persistence.entity.MBudget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 予算のドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BudgetMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity 予算エンティティ
     * @return 予算ドメインモデル
     */
    Budget toDomain(MBudget entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain 予算ドメインモデル
     * @return 予算エンティティ
     */
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MBudget toEntity(Budget domain);
}
