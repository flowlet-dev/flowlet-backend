package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.Budget;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 予算のリポジトリインターフェース。
 */
public interface BudgetRepository {
    /**
     * すべての予算を取得します。
     *
     * @return 予算リスト
     */
    List<Budget> findAll();

    /**
     * 指定されたIDの予算を取得します。
     *
     * @param budgetId 予算ID
     * @return 予算（存在しない場合は空）
     */
    Optional<Budget> findById(Long budgetId);

    /**
     * 指定されたカテゴリーと期間に重複する予算を取得します。
     *
     * @param categoryCd カテゴリーコード
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 予算リスト
     */
    List<Budget> findOverlapBudgets(String categoryCd, LocalDate startDate, LocalDate endDate);

    /**
     * 予算を保存します。
     *
     * @param budget 保存する予算
     * @return 保存された予算
     */
    Budget save(Budget budget);

    /**
     * 指定されたIDの予算を削除します。
     *
     * @param budgetId 予算ID
     */
    void deleteById(Long budgetId);
}
