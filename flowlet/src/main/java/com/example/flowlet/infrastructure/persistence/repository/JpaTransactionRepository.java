package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.TTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TTransaction, Long> {
}