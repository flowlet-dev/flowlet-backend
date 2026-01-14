package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.domain.repository.BudgetRepository;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import com.example.flowlet.domain.repository.TransactionRepository;
import com.example.flowlet.domain.repository.UserSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialSummaryServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserSettingRepository userSettingRepository;
    @Mock
    private RecurringRuleRepository recurringRuleRepository;
    @Mock
    private BudgetRepository budgetRepository;
    @Mock
    private AnnualExpenseSmoothingService annualExpenseSmoothingService;

    @InjectMocks
    private FinancialSummaryService financialSummaryService;

    @Test
    void testCalculateAvailableAmount() {
        // Setup
        LocalDate today = LocalDate.of(2026, 1, 15);
        LocalDate periodEndDate = LocalDate.of(2026, 1, 25);

        // 1. Current Liquid Assets
        VirtualAccount liquidAcc = new VirtualAccount(1L, "Wallet", true);
        TransactionDetail detail = new TransactionDetail(1L, BigDecimal.valueOf(100000), null, null, liquidAcc);
        Transaction t = new Transaction(1L, today, "Income", List.of(detail));
        when(transactionRepository.findAll()).thenReturn(List.of(t));

        // 2. Scheduled Expenses (Recurring Rules)
        Category outgoCat = new Category("FOOD", null, "Food", "OUTGO");
        RecurringRule rule = new RecurringRule(1L, "Rent", BigDecimal.valueOf(30000), 20, outgoCat, null, null, "Rent", true);
        when(recurringRuleRepository.findByIsActive(true)).thenReturn(List.of(rule));

        // 3. Savings Goal
        Budget budget = new Budget(1L, null, BigDecimal.valueOf(120000), null, null);
        when(budgetRepository.findAll()).thenReturn(List.of(budget));
        when(annualExpenseSmoothingService.calculateMonthlySavingAmount(BigDecimal.valueOf(120000))).thenReturn(BigDecimal.valueOf(10000));

        // Execution
        // Liquid(100,000) - Scheduled(30,000) - Savings(10,000) = 60,000
        BigDecimal availableAmount = financialSummaryService.calculateAvailableAmount(today, periodEndDate);

        // Verification
        assertEquals(0, BigDecimal.valueOf(60000).compareTo(availableAmount));
    }
}
