package com.example.flowlet.infrastructure.persistence.repository;

import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.domain.repository.PhysicalAccountRepository;
import com.example.flowlet.infrastructure.persistence.entity.MPhysicalAccount;
import com.example.flowlet.infrastructure.persistence.mapper.PhysicalAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class PhysicalAccountRepositoryImpl implements PhysicalAccountRepository {

    private final JpaPhysicalAccountRepository jpaRepository;
    private final PhysicalAccountMapper mapper;

    @Override
    public List<PhysicalAccount> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<PhysicalAccount> findById(Long physicalAccountId) {
        return jpaRepository.findById(physicalAccountId).map(mapper::toDomain);
    }

    @Override
    public PhysicalAccount save(PhysicalAccount physicalAccount) {
        MPhysicalAccount entity = mapper.toEntity(physicalAccount);
        MPhysicalAccount savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long physicalAccountId) {
        jpaRepository.deleteById(physicalAccountId);
    }
}