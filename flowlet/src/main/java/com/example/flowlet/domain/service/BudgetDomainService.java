package com.example.flowlet.domain.service;

import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.domain.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 予算に関するビジネスロジックを提供するドメインサービス。
 */
@Service
@RequiredArgsConstructor
public class BudgetDomainService {

    private final BudgetRepository budgetRepository;

    /**
     * 予算の妥当性を検証します。
     * 同一カテゴリーにおいて、期間が重複する予算が存在しないことを確認します。
     *
     * @param budget 検証対象の予算
     * @throws BusinessException バリデーションエラーの場合
     */
    public void validate(Budget budget) {
        if (budget.startDate().isAfter(budget.endDate())) {
            throw new BusinessException("error.budget.invalid_period");
        }

        List<Budget> overlapBudgets = budgetRepository.findOverlapBudgets(
                budget.category().categoryCd(),
                budget.startDate(),
                budget.endDate()
        );

        // 新規登録時、または更新時に自身以外の重複があるかチェック
        boolean hasOverlap = overlapBudgets.stream()
                .anyMatch(b -> !b.budgetId().equals(budget.budgetId()));

        if (hasOverlap) {
            throw new BusinessException("error.budget.overlap_period", budget.category().categoryName());
        }
    }
}
