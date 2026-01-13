package com.example.flowlet.domain.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 年間支出の平準化に関するビジネスロジックを提供するドメインサービス。
 */
@Service
public class AnnualExpenseSmoothingService {

    /**
     * 年1回の大きな支出から、月次の必要積立額を計算します。
     *
     * @param annualAmount 年間支出額
     * @return 月次の積立必要額
     */
    public BigDecimal calculateMonthlySavingAmount(BigDecimal annualAmount) {
        if (annualAmount == null || annualAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        // 12ヶ月で割り、小数点以下は切り上げ（確実に貯まるように）
        return annualAmount.divide(BigDecimal.valueOf(12), 0, RoundingMode.CEILING);
    }
}
