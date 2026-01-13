package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.VirtualAccountRequest;
import com.example.flowlet.application.dto.VirtualAccountResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.domain.repository.VirtualAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仮想口座に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VirtualAccountApplicationService {

    private final VirtualAccountRepository virtualAccountRepository;

    /**
     * すべての仮想口座を取得します。
     *
     * @return 仮想口座レスポンスDTOのリスト
     */
    public List<VirtualAccountResponse> findAll() {
        return virtualAccountRepository.findAll().stream()
                .map(VirtualAccountResponse::fromDomain)
                .toList();
    }

    /**
     * 指定されたIDの仮想口座を取得します。
     *
     * @param virtualAccountId 仮想口座ID
     * @return 仮想口座レスポンスDTO
     * @throws RuntimeException 仮想口座が見つからない場合
     */
    public VirtualAccountResponse findById(Long virtualAccountId) {
        try {
            return virtualAccountRepository.findById(virtualAccountId)
                    .map(VirtualAccountResponse::fromDomain)
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "仮想口座", virtualAccountId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 仮想口座を新規登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された仮想口座レスポンスDTO
     */
    @Transactional
    public VirtualAccountResponse create(VirtualAccountRequest request) {
        VirtualAccount domain = new VirtualAccount(
                null,
                request.accountName(),
                request.isLiquid()
        );
        VirtualAccount savedDomain = virtualAccountRepository.save(domain);
        return VirtualAccountResponse.fromDomain(savedDomain);
    }

    /**
     * 仮想口座を更新します。
     *
     * @param virtualAccountId 仮想口座ID
     * @param request 更新リクエストDTO
     * @return 更新された仮想口座レスポンスDTO
     */
    @Transactional
    public VirtualAccountResponse update(Long virtualAccountId, VirtualAccountRequest request) {
        try {
            if (virtualAccountRepository.findById(virtualAccountId).isEmpty()) {
                throw new BusinessException("error.resource.not_found", "仮想口座", virtualAccountId);
            }
            VirtualAccount domain = new VirtualAccount(
                    virtualAccountId,
                    request.accountName(),
                    request.isLiquid()
            );
            VirtualAccount updatedDomain = virtualAccountRepository.save(domain);
            return VirtualAccountResponse.fromDomain(updatedDomain);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 仮想口座を削除します。
     *
     * @param virtualAccountId 仮想口座ID
     */
    @Transactional
    public void delete(Long virtualAccountId) {
        virtualAccountRepository.deleteById(virtualAccountId);
    }
}
