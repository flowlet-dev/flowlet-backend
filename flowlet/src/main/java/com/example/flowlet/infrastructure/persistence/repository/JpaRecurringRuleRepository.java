package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MRecurringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRecurringRuleRepository extends JpaRepository<MRecurringRule, Long> {
    List<MRecurringRule> findByIsActive(Boolean isActive);
}
