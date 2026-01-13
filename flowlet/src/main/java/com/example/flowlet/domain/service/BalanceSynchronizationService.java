package com.example.flowlet.domain.service;

import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 資産同期（Balance Synchronization）に関するドメインサービス。
 * 実口座と仮想口座の残高整合性を管理します。
 */
@Service
@RequiredArgsConstructor
public class BalanceSynchronizationService {

    private final TransactionRepository transactionRepository;

    /**
     * 現在の資産状況が同期されているか（Σ実口座 = Σ仮想口座）を確認します。
     * すべての取引明細の合計が0であれば、理論上同期されていることになります。
     *
     * @return 同期されている場合は true
     */
    public boolean isSynchronized() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        BigDecimal totalAmount = allTransactions.stream()
                .flatMap(t -> t.details().stream())
                .map(TransactionDetail::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 全取引の明細金額を合計して、入出金がバランスしていれば同期されているとみなす
        // (収入はプラス、支出はマイナスの金額として記録される想定)
        // ただし、初期残高などの扱いによってこのロジックは調整が必要になる可能性がある。
        return totalAmount.compareTo(BigDecimal.ZERO) == 0;
    }
}
