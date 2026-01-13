package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MCode;
import com.example.flowlet.infrastructure.persistence.entity.CodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCodeRepository extends JpaRepository<MCode, CodeId> {
}