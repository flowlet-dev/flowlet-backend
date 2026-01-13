package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.Code;
import java.util.List;
import java.util.Optional;

/**
 * 汎用コードのリポジトリインターフェース。
 */
public interface CodeRepository {
    /**
     * すべての汎用コードを取得します。
     *
     * @return 汎用コードリスト
     */
    List<Code> findAll();

    /**
     * 指定されたIDの汎用コードを取得します。
     *
     * @param codeGroup コードグループ
     * @param codeValue コード値
     * @return 汎用コード（存在しない場合は空）
     */
    Optional<Code> findById(String codeGroup, String codeValue);

    /**
     * 汎用コードを保存します。
     *
     * @param code 保存する汎用コード
     * @return 保存された汎用コード
     */
    Code save(Code code);

    /**
     * 指定されたIDの汎用コードを削除します。
     *
     * @param codeGroup コードグループ
     * @param codeValue コード値
     */
    void deleteById(String codeGroup, String codeValue);
}
