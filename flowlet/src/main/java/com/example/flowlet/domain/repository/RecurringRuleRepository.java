package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.RecurringRule;
import java.util.List;
import java.util.Optional;

/**
 * 定期実行ルールのリポジトリインターフェース。
 */
public interface RecurringRuleRepository {
    List<RecurringRule> findAll();
    List<RecurringRule> findByIsActive(boolean isActive);
    Optional<RecurringRule> findById(Long id);
    RecurringRule save(RecurringRule recurringRule);
    void deleteById(Long id);
}
