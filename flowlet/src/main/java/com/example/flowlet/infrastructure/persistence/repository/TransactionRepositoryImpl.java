package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.repository.TransactionRepository;
import com.example.flowlet.infrastructure.persistence.entity.TTransaction;
import com.example.flowlet.infrastructure.persistence.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository jpaRepository;
    private final TransactionMapper mapper;

    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return jpaRepository.findById(transactionId).map(mapper::toDomain);
    }

    @Override
    public Transaction save(Transaction transaction) {
        TTransaction entity = mapper.toEntity(transaction);
        TTransaction savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long transactionId) {
        jpaRepository.deleteById(transactionId);
    }
}