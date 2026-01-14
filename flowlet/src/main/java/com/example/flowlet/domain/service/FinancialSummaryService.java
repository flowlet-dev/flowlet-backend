package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.Budget;
import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.model.UserSetting;
import com.example.flowlet.domain.repository.BudgetRepository;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import com.example.flowlet.domain.repository.TransactionRepository;
import com.example.flowlet.domain.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 財務集計に関するビジネスロジックを提供するドメインサービス。
 */
@Service
@RequiredArgsConstructor
public class FinancialSummaryService {

    private final TransactionRepository transactionRepository;
    private final UserSettingRepository userSettingRepository;
    private final RecurringRuleRepository recurringRuleRepository;
    private final BudgetRepository budgetRepository;
    private final AnnualExpenseSmoothingService annualExpenseSmoothingService;

    /**
     * 指定された日を含む集計期間（締め日に基づく）を取得します。
     *
     * @param targetDate 対象日
     * @return [開始日, 終了日]
     */
    public LocalDate[] calculateCurrentPeriod(LocalDate targetDate) {
        int closingDay = Integer.parseInt(userSettingRepository.findByKey("closing_day")
                .map(UserSetting::settingValue)
                .orElse("25"));

        LocalDate startDate;
        LocalDate endDate;

        if (targetDate.getDayOfMonth() > closingDay) {
            // 締め日を過ぎている場合、今月の締め日の翌日から来月の締め日まで
            startDate = targetDate.withDayOfMonth(closingDay).plusDays(1);
            endDate = targetDate.plusMonths(1).withDayOfMonth(closingDay);
        } else {
            // 締め日以前の場合、先月の締め日の翌日から今月の締め日まで
            startDate = targetDate.minusMonths(1).withDayOfMonth(closingDay).plusDays(1);
            endDate = targetDate.withDayOfMonth(closingDay);
        }

        return new LocalDate[]{startDate, endDate};
    }

    /**
     * 流動資産の現在合計額を計算します。
     * (isLiquid = true の仮想口座に紐づく取引明細の合計)
     *
     * @return 流動資産合計
     */
    public BigDecimal calculateCurrentLiquidAssets() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        return allTransactions.stream()
                .flatMap(t -> t.details().stream())
                .filter(d -> d.virtualAccount().isLiquid())
                .map(TransactionDetail::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 指定された期間内の収支合計を計算します。
     *
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 収支合計（収入はプラス、支出はマイナス）
     */
    public BigDecimal calculatePeriodBalance(LocalDate startDate, LocalDate endDate) {
        List<Transaction> allTransactions = transactionRepository.findAll();
        return allTransactions.stream()
                .filter(t -> !t.transactionDate().isBefore(startDate) && !t.transactionDate().isAfter(endDate))
                .flatMap(t -> t.details().stream())
                .map(TransactionDetail::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 指定された日（今日）から集計期間終了日までの予定支出合計を計算します。
     * 定期実行ルールに基づき計算します。
     *
     * @param today 今日
     * @param periodEndDate 集計期間終了日
     * @return 予定支出合計（正の値）
     */
    public BigDecimal calculateScheduledExpenses(LocalDate today, LocalDate periodEndDate) {
        List<RecurringRule> activeRules = recurringRuleRepository.findByIsActive(true);
        BigDecimal totalScheduled = BigDecimal.ZERO;

        for (RecurringRule rule : activeRules) {
            // 支出のみを対象とする
            if ("OUTGO".equals(rule.category().flowType())) {
                LocalDate nextOccurrence = calculateNextOccurrence(rule, today);
                // 次回実行日が期間内であれば加算
                if (nextOccurrence != null && !nextOccurrence.isAfter(periodEndDate)) {
                    totalScheduled = totalScheduled.add(rule.amount().abs());
                }
            }
        }
        return totalScheduled;
    }

    private LocalDate calculateNextOccurrence(RecurringRule rule, LocalDate today) {
        int day = rule.transactionDay();
        try {
            LocalDate occurrenceThisMonth = today.withDayOfMonth(day);
            if (!occurrenceThisMonth.isBefore(today)) {
                return occurrenceThisMonth;
            }
            return today.plusMonths(1).withDayOfMonth(day);
        } catch (java.time.DateTimeException e) {
            // 月末などの調整が必要な場合（例: 31日が無い月は月末に合わせる等）
            // ここでは簡易的に月末に調整
            LocalDate lastDayThisMonth = today.withDayOfMonth(today.lengthOfMonth());
            if (day > today.lengthOfMonth()) {
                if (!lastDayThisMonth.isBefore(today)) {
                    return lastDayThisMonth;
                }
            }
            return today.plusMonths(1).withDayOfMonth(today.plusMonths(1).lengthOfMonth());
        }
    }

    /**
     * 今月の積立目標額を計算します。
     * 年間予算（カテゴリー別）から月次の積立必要額を合計します。
     *
     * @return 積立目標合計
     */
    public BigDecimal calculateMonthlySavingsGoal() {
        List<Budget> allBudgets = budgetRepository.findAll();
        // ここでは簡易的に、全予算のうち「Savings (積立)」系のカテゴリーや
        // 特定のフラグがあるものを対象とする設計が望ましいが、
        // 現状のモデルに基づき、全予算から月次平準化額を算出する例とする。
        // ※実際には「積立用予算」のみを抽出するロジックが必要
        return allBudgets.stream()
                .map(b -> annualExpenseSmoothingService.calculateMonthlySavingAmount(b.amount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 自由に使える額（Available Amount）を計算します。
     * 計算式: 流動資産合計 - 期間終了までの予定支出 - 月次積立目標
     *
     * @param today 今日
     * @param periodEndDate 期間終了日
     * @return 自由に使える額
     */
    public BigDecimal calculateAvailableAmount(LocalDate today, LocalDate periodEndDate) {
        BigDecimal liquidAssets = calculateCurrentLiquidAssets();
        BigDecimal scheduledExpenses = calculateScheduledExpenses(today, periodEndDate);
        BigDecimal savingsGoal = calculateMonthlySavingsGoal();

        BigDecimal available = liquidAssets.subtract(scheduledExpenses).subtract(savingsGoal);
        
        // マイナスになる場合は0とする（またはそのまま返すかは要件次第）
        return available.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : available;
    }
}
