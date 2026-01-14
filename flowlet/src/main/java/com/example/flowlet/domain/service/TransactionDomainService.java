package com.example.flowlet.domain.service;

import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 取引に関するドメインサービス。
 * 取引の整合性検証などのビジネスロジックを提供します。
 */
@Service
public class TransactionDomainService {

    /**
     * 取引の整合性を検証します。
     * 1つの取引における全明細の金額が整合していることを確認します。
     *
     * @param transaction 検証対象の取引
     * @throws BusinessException 整合性が取れていない場合
     */
    public void validate(Transaction transaction) {
        if (transaction.details() == null || transaction.details().isEmpty()) {
            throw new BusinessException("error.transaction.no_details");
        }

        BigDecimal physicalTotal = BigDecimal.ZERO;
        BigDecimal virtualTotal = BigDecimal.ZERO;

        for (TransactionDetail detail : transaction.details()) {
            if (detail.amount() == null || detail.amount().compareTo(BigDecimal.ZERO) == 0) {
                throw new BusinessException("error.transaction.detail.zero_amount");
            }

            if (detail.physicalAccount() != null) {
                physicalTotal = physicalTotal.add(detail.amount());
            }
            if (detail.virtualAccount() != null) {
                virtualTotal = virtualTotal.add(detail.amount());
            }

            if (detail.physicalAccount() == null && detail.virtualAccount() == null) {
                throw new BusinessException("error.transaction.detail.no_account");
            }
        }

        // 実口座側の合計と仮想口座側の合計が一致していることを確認する
        // (実口座の増減 = 仮想口座の増減)
        if (physicalTotal.compareTo(virtualTotal) != 0) {
            throw new BusinessException("error.transaction.imbalance", physicalTotal, virtualTotal);
        }
    }
}
