package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.VirtualAccount;
import java.util.List;
import java.util.Optional;

/**
 * 仮想口座のリポジトリインターフェース。
 */
public interface VirtualAccountRepository {
    /**
     * すべての仮想口座を取得します。
     *
     * @return 仮想口座リスト
     */
    List<VirtualAccount> findAll();

    /**
     * 指定されたIDの仮想口座を取得します。
     *
     * @param id 仮想口座ID
     * @return 仮想口座（存在しない場合は空）
     */
    Optional<VirtualAccount> findById(Long id);

    /**
     * 仮想口座を保存します。
     *
     * @param virtualAccount 保存する仮想口座
     * @return 保存された仮想口座
     */
    VirtualAccount save(VirtualAccount virtualAccount);

    /**
     * 指定されたIDの仮想口座を削除します。
     *
     * @param id 仮想口座ID
     */
    void deleteById(Long id);
}
