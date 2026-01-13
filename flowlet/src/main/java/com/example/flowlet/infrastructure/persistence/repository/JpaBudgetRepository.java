package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaBudgetRepository extends JpaRepository<MBudget, Long> {

    @Query("SELECT b FROM MBudget b WHERE b.category.categoryCd = :categoryCd " +
           "AND b.startDate <= :endDate AND b.endDate >= :startDate")
    List<MBudget> findOverlapBudgets(
            @Param("categoryCd") String categoryCd,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
