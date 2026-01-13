package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.Code;
import com.example.flowlet.domain.repository.CodeRepository;
import com.example.flowlet.infrastructure.persistence.entity.CodeId;
import com.example.flowlet.infrastructure.persistence.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 汎用コードのリポジトリ実装クラス。
 */
@Repository
@Primary
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {

    private final JpaCodeRepository jpaRepository;
    private final CodeMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Code> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Code> findById(String codeGroup, String codeValue) {
        return jpaRepository.findById(new CodeId(codeGroup, codeValue)).map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Code save(Code code) {
        var entity = mapper.toEntity(code);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(String codeGroup, String codeValue) {
        jpaRepository.deleteById(new CodeId(codeGroup, codeValue));
    }
}