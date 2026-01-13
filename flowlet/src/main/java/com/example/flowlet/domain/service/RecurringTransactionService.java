package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import com.example.flowlet.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 定期記録の自動生成に関するビジネスロジックを提供するドメインサービス。
 */
@Service
@RequiredArgsConstructor
public class RecurringTransactionService {

    private final RecurringRuleRepository recurringRuleRepository;
    private final TransactionRepository transactionRepository;

    /**
     * 指定された日の定期記録を生成します。
     *
     * @param executionDate 実行日
     */
    public void generateRecurringTransactions(LocalDate executionDate) {
        List<RecurringRule> activeRules = recurringRuleRepository.findByIsActive(true);

        for (RecurringRule rule : activeRules) {
            if (rule.transactionDay() == executionDate.getDayOfMonth()) {
                // すでに登録済みかチェック（簡易的に同日・同ルール名でチェック）
                // ※本来は実行ログテーブルなどを持つのが望ましい
                boolean alreadyExists = transactionRepository.findAll().stream()
                        .anyMatch(t -> t.transactionDate().equals(executionDate) &&
                                     t.description() != null &&
                                     t.description().equals("[定期] " + rule.ruleName()));

                if (!alreadyExists) {
                    TransactionDetail detail = new TransactionDetail(
                            null,
                            rule.amount(),
                            rule.category(),
                            rule.physicalAccount(),
                            rule.virtualAccount()
                    );

                    Transaction transaction = new Transaction(
                            null,
                            executionDate,
                            "[定期] " + rule.ruleName(),
                            List.of(detail)
                    );

                    transactionRepository.save(transaction);
                }
            }
        }
    }
}
