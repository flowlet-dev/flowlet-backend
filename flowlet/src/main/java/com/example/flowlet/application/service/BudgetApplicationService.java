package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.BudgetRequest;
import com.example.flowlet.application.dto.BudgetResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.repository.BudgetRepository;
import com.example.flowlet.domain.repository.CategoryRepository;
import com.example.flowlet.domain.service.BudgetDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 予算に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetApplicationService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetDomainService budgetDomainService;

    /**
     * すべての予算を取得します。
     *
     * @return 予算レスポンスDTOのリスト
     */
    public List<BudgetResponse> findAll() {
        return budgetRepository.findAll().stream()
                .map(BudgetResponse::fromDomain)
                .toList();
    }

    /**
     * 指定されたIDの予算を取得します。
     *
     * @param budgetId 予算ID
     * @return 予算レスポンスDTO
     */
    public BudgetResponse findById(Long budgetId) {
        try {
            return budgetRepository.findById(budgetId)
                    .map(BudgetResponse::fromDomain)
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "予算", budgetId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 予算を登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された予算レスポンスDTO
     */
    @Transactional
    public BudgetResponse create(BudgetRequest request) {
        try {
            Category category = categoryRepository.findById(request.categoryCd())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "カテゴリー", request.categoryCd()));

            Budget budget = new Budget(
                    null,
                    category,
                    request.amount(),
                    request.startDate(),
                    request.endDate()
            );

            budgetDomainService.validate(budget);

            Budget savedBudget = budgetRepository.save(budget);
            return BudgetResponse.fromDomain(savedBudget);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 予算を更新します。
     *
     * @param budgetId 予算ID
     * @param request 更新リクエストDTO
     * @return 更新された予算レスポンスDTO
     */
    @Transactional
    public BudgetResponse update(Long budgetId, BudgetRequest request) {
        try {
            if (budgetRepository.findById(budgetId).isEmpty()) {
                throw new BusinessException("error.resource.not_found", "予算", budgetId);
            }

            Category category = categoryRepository.findById(request.categoryCd())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "カテゴリー", request.categoryCd()));

            Budget budget = new Budget(
                    budgetId,
                    category,
                    request.amount(),
                    request.startDate(),
                    request.endDate()
            );

            budgetDomainService.validate(budget);

            Budget updatedBudget = budgetRepository.save(budget);
            return BudgetResponse.fromDomain(updatedBudget);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 予算を削除します。
     *
     * @param budgetId 予算ID
     */
    @Transactional
    public void delete(Long budgetId) {
        budgetRepository.deleteById(budgetId);
    }
}
