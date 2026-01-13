package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.Category;
import java.util.List;
import java.util.Optional;

/**
 * カテゴリーのリポジトリインターフェース。
 */
public interface CategoryRepository {
    /**
     * すべてのカテゴリーを取得します。
     *
     * @return カテゴリーリスト
     */
    List<Category> findAll();

    /**
     * 指定されたIDのカテゴリーを取得します。
     *
     * @param id カテゴリーID
     * @return カテゴリー（存在しない場合は空）
     */
    Optional<Category> findById(String id);

    /**
     * カテゴリーを保存します。
     *
     * @param category 保存するカテゴリー
     * @return 保存されたカテゴリー
     */
    Category save(Category category);

    /**
     * 指定されたIDのカテゴリーを削除します。
     *
     * @param id カテゴリーID
     */
    void deleteById(String id);
}
