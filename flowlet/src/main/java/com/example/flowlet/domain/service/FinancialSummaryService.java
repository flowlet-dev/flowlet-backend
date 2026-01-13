package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.model.UserSetting;
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
}
