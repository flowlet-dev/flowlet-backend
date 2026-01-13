package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.domain.repository.VirtualAccountRepository;
import com.example.flowlet.infrastructure.persistence.mapper.VirtualAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class VirtualAccountRepositoryImpl implements VirtualAccountRepository {

    private final JpaVirtualAccountRepository jpaRepository;
    private final VirtualAccountMapper mapper;

    @Override
    public List<VirtualAccount> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<VirtualAccount> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public VirtualAccount save(VirtualAccount virtualAccount) {
        var entity = mapper.toEntity(virtualAccount);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}