package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.infrastructure.persistence.entity.MRecurringRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 定期実行ルールのドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, PhysicalAccountMapper.class, VirtualAccountMapper.class})
public interface RecurringRuleMapper {

    RecurringRule toDomain(MRecurringRule entity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    MRecurringRule toEntity(RecurringRule domain);
}
