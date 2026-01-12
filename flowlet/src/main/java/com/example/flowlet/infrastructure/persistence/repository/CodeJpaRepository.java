package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MCode;
import com.example.flowlet.infrastructure.persistence.entity.MCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeJpaRepository extends JpaRepository<MCode, MCodeId> {
}
