package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.FinancialSummaryResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.service.FinancialSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 財務統計に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
public class FinancialSummaryApplicationService {

    private final FinancialSummaryService financialSummaryService;

    /**
     * 現在の財務サマリーを取得します。
     *
     * @return 財務サマリーレスポンスDTO
     */
    public FinancialSummaryResponse getCurrentSummary() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate[] period = financialSummaryService.calculateCurrentPeriod(today);
            LocalDate startDate = period[0];
            LocalDate endDate = period[1];

            BigDecimal currentLiquidAssets = financialSummaryService.calculateCurrentLiquidAssets();
            BigDecimal periodBalance = financialSummaryService.calculatePeriodBalance(startDate, endDate);

            // 「あといくら使えるか」の計算ロジック
            // 現時点では簡易的に「流動資産合計」を表示する
            // 将来的には「予定支出」や「積立目標」を差し引くロジックを追加する

            return new FinancialSummaryResponse(
                    startDate,
                    endDate,
                    currentLiquidAssets,
                    periodBalance,
                    currentLiquidAssets
            );
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }
}
