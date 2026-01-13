package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.FinancialSummaryResponse;
import com.example.flowlet.application.service.FinancialSummaryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 財務集計・サマリーに関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/financial-summary")
@RequiredArgsConstructor
@Tag(name = "Financial Summary", description = "財務集計（サマリー）API")
public class FinancialSummaryController {

    private final FinancialSummaryApplicationService financialSummaryApplicationService;

    /**
     * 現在の財務サマリーを取得します。
     *
     * @return 財務サマリーレスポンスDTO
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "財務サマリー取得", description = "締め日に基づく今月の集計期間、流動資産合計、期間収支、自由に使える額を取得します。")
    public FinancialSummaryResponse getCurrentSummary() {
        return financialSummaryApplicationService.getCurrentSummary();
    }
}
