package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.domain.repository.BudgetRepository;
import com.example.flowlet.infrastructure.persistence.entity.MBudget;
import com.example.flowlet.infrastructure.persistence.mapper.BudgetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 予算のリポジトリ実装クラス。
 */
@Repository
@Primary
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepository {

    private final JpaBudgetRepository jpaRepository;
    private final BudgetMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Budget> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Budget> findById(Long budgetId) {
        return jpaRepository.findById(budgetId).map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Budget> findOverlapBudgets(String categoryCd, LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findOverlapBudgets(categoryCd, startDate, endDate).stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Budget save(Budget budget) {
        MBudget entity = mapper.toEntity(budget);
        MBudget savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long budgetId) {
        jpaRepository.deleteById(budgetId);
    }
}
