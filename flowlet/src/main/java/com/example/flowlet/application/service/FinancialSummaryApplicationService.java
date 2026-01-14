package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.FinancialSummaryResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.service.FinancialSummaryService;
import com.example.flowlet.domain.service.TransferSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 財務統計に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
public class FinancialSummaryApplicationService {

    private final FinancialSummaryService financialSummaryService;
    private final TransferSuggestionService transferSuggestionService;

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
            BigDecimal availableAmount = financialSummaryService.calculateAvailableAmount(today, endDate);

            // アラートの取得
            List<String> alerts = transferSuggestionService.detectBalanceAlerts(endDate);

            return new FinancialSummaryResponse(
                    startDate,
                    endDate,
                    currentLiquidAssets,
                    periodBalance,
                    availableAmount,
                    alerts
            );
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }
}
