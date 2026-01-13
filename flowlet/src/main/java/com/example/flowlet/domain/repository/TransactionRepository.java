package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.Transaction;
import java.util.List;
import java.util.Optional;

/**
 * 取引のリポジトリインターフェース。
 */
public interface TransactionRepository {
    /**
     * すべての取引を取得します。
     *
     * @return 取引リスト
     */
    List<Transaction> findAll();

    /**
     * 指定されたIDの取引を取得します。
     *
     * @param id 取引ID
     * @return 取引（存在しない場合は空）
     */
    Optional<Transaction> findById(Long id);

    /**
     * 取引を保存します。
     *
     * @param transaction 保存する取引
     * @return 保存された取引
     */
    Transaction save(Transaction transaction);

    /**
     * 指定されたIDの取引を削除します。
     *
     * @param id 取引ID
     */
    void deleteById(Long id);
}