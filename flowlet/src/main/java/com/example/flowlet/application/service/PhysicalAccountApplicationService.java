package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.PhysicalAccountRequest;
import com.example.flowlet.application.dto.PhysicalAccountResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.domain.repository.PhysicalAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 実口座に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhysicalAccountApplicationService {

    private final PhysicalAccountRepository physicalAccountRepository;

    /**
     * すべての実口座を取得します。
     *
     * @return 実口座レスポンスDTOのリスト
     */
    public List<PhysicalAccountResponse> findAll() {
        return physicalAccountRepository.findAll().stream()
                .map(PhysicalAccountResponse::fromDomain)
                .toList();
    }

    /**
     * 指定されたIDの実口座を取得します。
     *
     * @param physicalAccountId 実口座ID
     * @return 実口座レスポンスDTO
     * @throws RuntimeException 実口座が見つからない場合
     */
    public PhysicalAccountResponse findById(Long physicalAccountId) {
        try {
            return physicalAccountRepository.findById(physicalAccountId)
                    .map(PhysicalAccountResponse::fromDomain)
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "実口座", physicalAccountId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 実口座を新規登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された実口座レスポンスDTO
     */
    @Transactional
    public PhysicalAccountResponse create(PhysicalAccountRequest request) {
        PhysicalAccount domain = new PhysicalAccount(
                null,
                request.accountName(),
                request.accountType()
        );
        PhysicalAccount savedDomain = physicalAccountRepository.save(domain);
        return PhysicalAccountResponse.fromDomain(savedDomain);
    }

    /**
     * 実口座を更新します。
     *
     * @param physicalAccountId 実口座ID
     * @param request 更新リクエストDTO
     * @return 更新された実口座レスポンスDTO
     */
    @Transactional
    public PhysicalAccountResponse update(Long physicalAccountId, PhysicalAccountRequest request) {
        try {
            if (physicalAccountRepository.findById(physicalAccountId).isEmpty()) {
                throw new BusinessException("error.resource.not_found", "実口座", physicalAccountId);
            }
            PhysicalAccount domain = new PhysicalAccount(
                    physicalAccountId,
                    request.accountName(),
                    request.accountType()
            );
            PhysicalAccount updatedDomain = physicalAccountRepository.save(domain);
            return PhysicalAccountResponse.fromDomain(updatedDomain);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 実口座を削除します。
     *
     * @param physicalAccountId 実口座ID
     */
    @Transactional
    public void delete(Long physicalAccountId) {
        physicalAccountRepository.deleteById(physicalAccountId);
    }
}
