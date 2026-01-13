package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import com.example.flowlet.infrastructure.persistence.entity.MRecurringRule;
import com.example.flowlet.infrastructure.persistence.mapper.RecurringRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class RecurringRuleRepositoryImpl implements RecurringRuleRepository {

    private final JpaRecurringRuleRepository jpaRepository;
    private final RecurringRuleMapper mapper;

    @Override
    public List<RecurringRule> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<RecurringRule> findByIsActive(boolean isActive) {
        return jpaRepository.findByIsActive(isActive).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<RecurringRule> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public RecurringRule save(RecurringRule recurringRule) {
        MRecurringRule entity = mapper.toEntity(recurringRule);
        MRecurringRule savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
