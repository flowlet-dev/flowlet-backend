package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.PhysicalAccount;
import java.util.List;
import java.util.Optional;

/**
 * 実口座のリポジトリインターフェース。
 */
public interface PhysicalAccountRepository {
    /**
     * すべての実口座を取得します。
     *
     * @return 実口座リスト
     */
    List<PhysicalAccount> findAll();

    /**
     * 指定されたIDの実口座を取得します。
     *
     * @param id 実口座ID
     * @return 実口座（存在しない場合は空）
     */
    Optional<PhysicalAccount> findById(Long id);

    /**
     * 実口座を保存します。
     *
     * @param physicalAccount 保存する実口座
     * @return 保存された実口座
     */
    PhysicalAccount save(PhysicalAccount physicalAccount);

    /**
     * 指定されたIDの実口座を削除します。
     *
     * @param id 実口座ID
     */
    void deleteById(Long id);
}
