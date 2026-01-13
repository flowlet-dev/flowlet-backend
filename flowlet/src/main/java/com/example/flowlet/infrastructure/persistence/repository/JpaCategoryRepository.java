package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<MCategory, String> {
}