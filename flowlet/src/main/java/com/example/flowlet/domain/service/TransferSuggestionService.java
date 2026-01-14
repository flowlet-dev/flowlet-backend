package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.domain.model.RecurringRule;
import com.example.flowlet.domain.repository.PhysicalAccountRepository;
import com.example.flowlet.domain.repository.RecurringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 資金移動の提案と残高アラートに関するビジネスロジックを提供するドメインサービス。
 */
@Service
@RequiredArgsConstructor
public class TransferSuggestionService {

    private final BalanceSynchronizationService balanceSynchronizationService;
    private final RecurringRuleRepository recurringRuleRepository;
    private final PhysicalAccountRepository physicalAccountRepository;

    /**
     * 指定された日付までの残高不足リスクを検知し、アラートおよび振替提案メッセージのリストを返します。
     *
     * @param untilDate 判定終了日
     * @return メッセージリスト
     */
    public List<String> detectBalanceAlerts(LocalDate untilDate) {
        List<String> messages = new ArrayList<>();
        List<PhysicalAccount> accounts = physicalAccountRepository.findAll();
        LocalDate today = LocalDate.now();

        List<AccountDeficit> deficits = new ArrayList<>();
        List<AccountSurplus> surpluses = new ArrayList<>();

        for (PhysicalAccount account : accounts) {
            BigDecimal currentBalance = balanceSynchronizationService.calculatePhysicalAccountBalance(account.physicalAccountId());
            BigDecimal scheduledOutgo = calculateScheduledOutgo(account.physicalAccountId(), today, untilDate);
            BigDecimal projectedBalance = currentBalance.add(scheduledOutgo);

            if (projectedBalance.compareTo(BigDecimal.ZERO) < 0) {
                messages.add(String.format("口座「%s」の残高が不足する可能性があります（予定支出合計: %s, 現在残高: %s）",
                        account.accountName(), scheduledOutgo.abs(), currentBalance));
                deficits.add(new AccountDeficit(account, projectedBalance.abs()));
            } else {
                surpluses.add(new AccountSurplus(account, projectedBalance));
            }
        }

        // 振替提案の作成
        for (AccountDeficit deficit : deficits) {
            BigDecimal needed = deficit.amount;
            for (AccountSurplus surplus : surpluses) {
                if (needed.compareTo(BigDecimal.ZERO) <= 0) break;

                BigDecimal moveAmount = surplus.available.min(needed);
                if (moveAmount.compareTo(BigDecimal.ZERO) > 0) {
                    messages.add(String.format("提案: 口座「%s」から「%s」へ %s 円を振り替えてください",
                            surplus.account.accountName(), deficit.account.accountName(), moveAmount));
                    surplus.available = surplus.available.subtract(moveAmount);
                    needed = needed.subtract(moveAmount);
                }
            }
            if (needed.compareTo(BigDecimal.ZERO) > 0) {
                messages.add(String.format("警告: 全口座の余剰資金を合わせても、口座「%s」の不足分 %s 円を補填できません",
                        deficit.account.accountName(), needed));
            }
        }

        return messages;
    }

    private record AccountDeficit(PhysicalAccount account, BigDecimal amount) {}
    private static class AccountSurplus {
        final PhysicalAccount account;
        BigDecimal available;
        AccountSurplus(PhysicalAccount account, BigDecimal available) {
            this.account = account;
            this.available = available;
        }
    }

    private BigDecimal calculateScheduledOutgo(Long physicalAccountId, LocalDate from, LocalDate to) {
        List<RecurringRule> rules = recurringRuleRepository.findByIsActive(true);
        BigDecimal totalOutgo = BigDecimal.ZERO;

        for (RecurringRule rule : rules) {
            if (rule.physicalAccount() != null && rule.physicalAccount().physicalAccountId().equals(physicalAccountId)) {
                if ("OUTGO".equals(rule.category().flowType())) {
                    // 簡易的に実行日が期間内かチェック
                    // 本来は複数回実行される可能性も考慮すべきだが、ここでは1回分とする
                    if (isDayInRange(rule.transactionDay(), from, to)) {
                        totalOutgo = totalOutgo.add(rule.amount()); // 支出は負の数と想定
                    }
                }
            }
        }
        return totalOutgo;
    }

    private boolean isDayInRange(int day, LocalDate from, LocalDate to) {
        LocalDate current = from;
        while (!current.isAfter(to)) {
            if (current.getDayOfMonth() == day) {
                return true;
            }
            current = current.plusDays(1);
        }
        return false;
    }
}
