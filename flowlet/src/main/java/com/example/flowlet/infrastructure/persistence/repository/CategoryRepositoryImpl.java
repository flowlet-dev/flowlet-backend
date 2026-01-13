package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.repository.CategoryRepository;
import com.example.flowlet.infrastructure.persistence.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * カテゴリーのリポジトリ実装クラス。
 */
@Repository
@Primary
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaRepository;
    private final CategoryMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category save(Category category) {
        var entity = mapper.toEntity(category);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }
}