package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for Category (Infrastructure Layer).
 */
@Repository
public interface CategoryJpaRepository extends JpaRepository<MCategory, String> {
}
