package com.example.flowlet.domain.repository;

import com.example.flowlet.domain.model.transaction.TTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 取引を操作するリポジトリ。
 */
public interface TransactionRepository extends JpaRepository<TTransaction, Long> {
}
