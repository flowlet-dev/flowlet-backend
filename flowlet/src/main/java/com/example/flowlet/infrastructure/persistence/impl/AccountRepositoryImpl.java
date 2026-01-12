package com.example.flowlet.infrastructure.persistence.impl;

import com.example.flowlet.domain.account.AccountRepository;
import com.example.flowlet.infrastructure.persistence.PersistenceMapper;
import com.example.flowlet.infrastructure.persistence.repository.PhysicalAccountJpaRepository;
import com.example.flowlet.infrastructure.persistence.repository.VirtualAccountJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final PhysicalAccountJpaRepository physicalJpaRepository;
    private final VirtualAccountJpaRepository virtualJpaRepository;
    private final PersistenceMapper mapper;

}
