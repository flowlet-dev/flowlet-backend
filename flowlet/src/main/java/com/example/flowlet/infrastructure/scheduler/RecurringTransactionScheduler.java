package com.example.flowlet.infrastructure.scheduler;

import com.example.flowlet.domain.service.RecurringTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 定期実行ルールに基づき取引を自動生成するスケジューラー。
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringTransactionScheduler {

    private final RecurringTransactionService recurringTransactionService;

    /**
     * 毎日深夜0時5分に定期実行ルールをチェックし、取引を生成します。
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void executeRecurringTasks() {
        log.info("Starting recurring transaction generation...");
        LocalDate today = LocalDate.now();
        try {
            recurringTransactionService.generateRecurringTransactions(today);
            log.info("Finished recurring transaction generation successfully.");
        } catch (Exception e) {
            log.error("Error occurred during recurring transaction generation: {}", e.getMessage(), e);
        }
    }
}
