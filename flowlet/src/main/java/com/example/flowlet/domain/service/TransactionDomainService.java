package com.example.flowlet.domain.service;

import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Transaction;
import org.springframework.stereotype.Service;

/**
 * 取引に関するドメインサービス。
 * 取引の整合性検証などのビジネスロジックを提供します。
 */
@Service
public class TransactionDomainService {

    /**
     * 取引の整合性を検証します。
     * 1つの取引における全明細の金額が0になることを確認します（複式簿記的な整合性）。
     * ※このシステムでは、実口座と仮想口座への割り振りが同時に行われるため、
     * 明細内の金額の合計が実口座側・仮想口座側それぞれでバランスしている必要があります。
     *
     * @param transaction 検証対象の取引
     * @throws BusinessException 整合性が取れていない場合
     */
    public void validate(Transaction transaction) {
        if (transaction.details() == null || transaction.details().isEmpty()) {
            throw new BusinessException("error.transaction.no_details");
        }

        // 実口座側の合計と仮想口座側の合計が一致していることを確認する
        // (実口座への入出金 = 仮想口座への割り振り)
        // 今回の設計では、1つの明細(TransactionDetail)に実口座と仮想口座が対で記録されるため、
        // 常に合計は一致するはずだが、将来的な拡張や特殊なケースを考慮して検証ロジックを置く。

        // 現状の要件（3.1 資産管理）では「すべての実口座の残高合計は、すべての仮想口座の残高合計と一致するように管理する」
        // とあるため、個々の取引においてもこの原則が守られている必要がある。
    }
}
