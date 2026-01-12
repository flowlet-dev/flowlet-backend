package com.example.flowlet.infrastructure.persistence.impl;

import com.example.flowlet.domain.common.CodeRepository;
import com.example.flowlet.infrastructure.persistence.PersistenceMapper;
import com.example.flowlet.infrastructure.persistence.repository.CodeJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {

    private final CodeJpaRepository jpaRepository;
    private final PersistenceMapper mapper;

}
