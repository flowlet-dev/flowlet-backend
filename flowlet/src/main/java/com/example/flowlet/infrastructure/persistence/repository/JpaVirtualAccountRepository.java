package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.infrastructure.persistence.entity.MVirtualAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaVirtualAccountRepository extends JpaRepository<MVirtualAccount, Long> {
}