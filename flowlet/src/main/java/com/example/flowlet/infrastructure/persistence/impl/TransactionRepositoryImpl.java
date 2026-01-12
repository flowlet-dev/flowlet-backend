package com.example.flowlet.infrastructure.persistence.impl;

import com.example.flowlet.domain.transaction.Transaction;
import com.example.flowlet.domain.transaction.TransactionRepository;
import com.example.flowlet.infrastructure.persistence.PersistenceMapper;
import com.example.flowlet.infrastructure.persistence.entity.TTransaction;
import com.example.flowlet.infrastructure.persistence.entity.TTransactionDetail;
import com.example.flowlet.infrastructure.persistence.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;
    private final PersistenceMapper mapper;

    @Override
    public void save(Transaction transaction) {
        TTransaction entity = mapper.transactionToEntity(transaction);
        // MapStruct ignores details, so we handle bidirectional relationship here
        transaction.details().forEach(detail -> {
            TTransactionDetail detailEntity = mapper.transactionDetailToEntity(detail);
            entity.addDetail(detailEntity);
        });
        jpaRepository.save(entity);
    }
}
