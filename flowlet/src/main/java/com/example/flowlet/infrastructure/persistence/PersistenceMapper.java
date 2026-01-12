package com.example.flowlet.infrastructure.persistence;

import com.example.flowlet.domain.transaction.Transaction;
import com.example.flowlet.domain.transaction.TransactionDetail;
import com.example.flowlet.infrastructure.persistence.entity.TTransaction;
import com.example.flowlet.infrastructure.persistence.entity.TTransactionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct Mapper for converting between Domain Models and JPA Entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersistenceMapper {


    // Transaction
    @Mapping(target = "details", ignore = true)
    TTransaction transactionToEntity(Transaction domain);

    TTransactionDetail transactionDetailToEntity(TransactionDetail domain);

}
