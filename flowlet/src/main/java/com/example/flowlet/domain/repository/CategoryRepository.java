package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.category.MCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * カテゴリーマスタを操作するリポジトリ。
 */
public interface CategoryRepository extends JpaRepository<MCategory, String> {
}
