package com.example.flowlet.infrastructure.persistence.impl;

import com.example.flowlet.domain.category.CategoryRepository;
import com.example.flowlet.infrastructure.persistence.PersistenceMapper;
import com.example.flowlet.infrastructure.persistence.repository.CategoryJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation for CategoryRepository.
 */
@Getter
@Component
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final PersistenceMapper mapper;

}
