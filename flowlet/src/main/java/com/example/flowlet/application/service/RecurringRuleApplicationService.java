package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.RecurringRuleRequest;
import com.example.flowlet.application.dto.RecurringRuleResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.domain.repository.CategoryRepository;
import com.example.flowlet.domain.repository.PhysicalAccountRepository;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import com.example.flowlet.domain.repository.VirtualAccountRepository;
import com.example.flowlet.domain.service.RecurringTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 定期実行ルールに関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecurringRuleApplicationService {

    private final RecurringRuleRepository recurringRuleRepository;
    private final CategoryRepository categoryRepository;
    private final PhysicalAccountRepository physicalAccountRepository;
    private final VirtualAccountRepository virtualAccountRepository;
    private final RecurringTransactionService recurringTransactionService;

    public List<RecurringRuleResponse> findAll() {
        return recurringRuleRepository.findAll().stream()
                .map(RecurringRuleResponse::fromDomain)
                .toList();
    }

    public RecurringRuleResponse findById(Long recurringRuleId) {
        try {
            return recurringRuleRepository.findById(recurringRuleId)
                    .map(RecurringRuleResponse::fromDomain)
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "定期実行ルール", recurringRuleId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    @Transactional
    public RecurringRuleResponse create(RecurringRuleRequest request) {
        try {
            Category category = categoryRepository.findById(request.categoryCd())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "カテゴリー", request.categoryCd()));
            PhysicalAccount physicalAccount = physicalAccountRepository.findById(request.physicalAccountId())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "実口座", request.physicalAccountId()));
            VirtualAccount virtualAccount = virtualAccountRepository.findById(request.virtualAccountId())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "仮想口座", request.virtualAccountId()));

            RecurringRule rule = new RecurringRule(
                    null,
                    request.ruleName(),
                    request.amount(),
                    request.transactionDay(),
                    category,
                    physicalAccount,
                    virtualAccount,
                    request.description(),
                    request.isActive()
            );

            RecurringRule savedRule = recurringRuleRepository.save(rule);
            return RecurringRuleResponse.fromDomain(savedRule);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    @Transactional
    public RecurringRuleResponse update(Long recurringRuleId, RecurringRuleRequest request) {
        try {
            if (recurringRuleRepository.findById(recurringRuleId).isEmpty()) {
                throw new BusinessException("error.resource.not_found", "定期実行ルール", recurringRuleId);
            }

            Category category = categoryRepository.findById(request.categoryCd())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "カテゴリー", request.categoryCd()));
            PhysicalAccount physicalAccount = physicalAccountRepository.findById(request.physicalAccountId())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "実口座", request.physicalAccountId()));
            VirtualAccount virtualAccount = virtualAccountRepository.findById(request.virtualAccountId())
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "仮想口座", request.virtualAccountId()));

            RecurringRule rule = new RecurringRule(
                    recurringRuleId,
                    request.ruleName(),
                    request.amount(),
                    request.transactionDay(),
                    category,
                    physicalAccount,
                    virtualAccount,
                    request.description(),
                    request.isActive()
            );

            RecurringRule updatedRule = recurringRuleRepository.save(rule);
            return RecurringRuleResponse.fromDomain(updatedRule);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    @Transactional
    public void delete(Long recurringRuleId) {
        recurringRuleRepository.deleteById(recurringRuleId);
    }

    /**
     * 定期実行ルールを手動で即時実行します（テスト用または即時反映用）。
     */
    @Transactional
    public void executeNow() {
        recurringTransactionService.generateRecurringTransactions(LocalDate.now());
    }
}
