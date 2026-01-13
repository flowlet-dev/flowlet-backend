package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.TransactionRequest;
import com.example.flowlet.application.dto.TransactionResponse;
import com.example.flowlet.domain.exception.BusinessException;
import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.model.PhysicalAccount;
import com.example.flowlet.domain.model.Transaction;
import com.example.flowlet.domain.model.TransactionDetail;
import com.example.flowlet.domain.model.VirtualAccount;
import com.example.flowlet.domain.repository.CategoryRepository;
import com.example.flowlet.domain.repository.PhysicalAccountRepository;
import com.example.flowlet.domain.repository.TransactionRepository;
import com.example.flowlet.domain.repository.VirtualAccountRepository;
import com.example.flowlet.domain.service.TransactionDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 取引に関するユースケースを提供するアプリケーションサービス。
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionApplicationService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final PhysicalAccountRepository physicalAccountRepository;
    private final VirtualAccountRepository virtualAccountRepository;
    private final TransactionDomainService transactionDomainService;

    /**
     * すべての取引を取得します。
     *
     * @return 取引レスポンスDTOのリスト
     */
    public List<TransactionResponse> findAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionResponse::fromDomain)
                .toList();
    }

    /**
     * 指定されたIDの取引を取得します。
     *
     * @param transactionId 取引ID
     * @return 取引レスポンスDTO
     */
    public TransactionResponse findById(Long transactionId) {
        try {
            return transactionRepository.findById(transactionId)
                    .map(TransactionResponse::fromDomain)
                    .orElseThrow(() -> new BusinessException("error.resource.not_found", "取引", transactionId));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 取引を登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された取引レスポンスDTO
     */
    @Transactional
    public TransactionResponse create(TransactionRequest request) {
        try {
            List<TransactionDetail> details = request.details().stream()
                    .map(detailRequest -> {
                        Category category = categoryRepository.findById(detailRequest.categoryCd())
                                .orElseThrow(() -> new BusinessException("error.resource.not_found", "カテゴリー", detailRequest.categoryCd()));
                        PhysicalAccount physicalAccount = physicalAccountRepository.findById(detailRequest.physicalAccountId())
                                .orElseThrow(() -> new BusinessException("error.resource.not_found", "実口座", detailRequest.physicalAccountId()));
                        VirtualAccount virtualAccount = virtualAccountRepository.findById(detailRequest.virtualAccountId())
                                .orElseThrow(() -> new BusinessException("error.resource.not_found", "仮想口座", detailRequest.virtualAccountId()));

                        return new TransactionDetail(
                                null,
                                detailRequest.amount(),
                                category,
                                physicalAccount,
                                virtualAccount
                        );
                    })
                    .toList();

            Transaction transaction = new Transaction(
                    null,
                    request.transactionDate(),
                    request.description(),
                    details
            );

            // ドメインサービスによる検証
            transactionDomainService.validate(transaction);

            Transaction savedTransaction = transactionRepository.save(transaction);
            return TransactionResponse.fromDomain(savedTransaction);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("error.unexpected", e.getMessage());
        }
    }

    /**
     * 取引を削除します。
     *
     * @param transactionId 取引ID
     */
    @Transactional
    public void delete(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
