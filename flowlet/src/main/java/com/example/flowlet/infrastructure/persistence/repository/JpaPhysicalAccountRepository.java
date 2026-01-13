package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MPhysicalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPhysicalAccountRepository extends JpaRepository<MPhysicalAccount, Long> {
}