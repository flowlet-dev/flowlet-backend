package com.example.flowlet.infrastructure.persistence.mapper;

import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.infrastructure.persistence.entity.TTransaction;
import com.example.flowlet.infrastructure.persistence.entity.TTransactionDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * 取引のドメインモデルとエンティティを変換するマッパーインターフェース。
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, PhysicalAccountMapper.class, VirtualAccountMapper.class})
public interface TransactionMapper {

    /**
     * エンティティをドメインモデルに変換します。
     *
     * @param entity 取引エンティティ
     * @return 取引ドメインモデル
     */
    Transaction toDomain(TTransaction entity);

    /**
     * 明細エンティティをドメインモデルに変換します。
     *
     * @param entity 明細エンティティ
     * @return 明細ドメインモデル
     */
    TransactionDetail toDomain(TTransactionDetail entity);

    /**
     * ドメインモデルをエンティティに変換します。
     *
     * @param domain 取引ドメインモデル
     * @return 取引エンティティ
     */
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    TTransaction toEntity(Transaction domain);

    /**
     * 取引エンティティへのマッピング後に明細を紐付けます。
     *
     * @param domain 取引ドメインモデル
     * @param entity 取引エンティティ
     */
    @AfterMapping
    default void afterToEntity(Transaction domain, @MappingTarget TTransaction entity) {
        if (domain.details() != null) {
            domain.details().forEach(detailDomain -> {
                TTransactionDetail detailEntity = toEntity(detailDomain);
                entity.addDetail(detailEntity);
            });
        }
    }

    /**
     * 明細ドメインモデルをエンティティに変換します。
     *
     * @param domain 明細ドメインモデル
     * @return 明細エンティティ
     */
    @Mapping(target = "transaction", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    TTransactionDetail toEntity(TransactionDetail domain);
}
