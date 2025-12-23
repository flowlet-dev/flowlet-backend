package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.TransactionRequest;
import com.example.flowlet.domain.model.category.MCategory;
import com.example.flowlet.domain.model.flow.FlowType;
import com.example.flowlet.domain.model.transaction.TTransaction;
import com.example.flowlet.domain.model.transaction.TransactionAmount;
import com.example.flowlet.domain.model.transaction.TransactionDate;
import com.example.flowlet.domain.repository.CategoryRepository;
import com.example.flowlet.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 取引に関するユースケースを提供するアプリケーションサービス。
 */
@Service
public class TransactionApplicationService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionApplicationService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 取引を登録する。
     *
     * @param request 取引登録リクエスト
     */
    @Transactional
    public void register(TransactionRequest request) {

        FlowType flowType = FlowType.valueOf(request.getFlowType());

        MCategory category = categoryRepository.findById(request.getCategoryCd())
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found."));

        TTransaction transaction = new TTransaction(
                new TransactionAmount(request.getAmount()),
                new TransactionDate(request.getTransactionDate()),
                flowType,
                category
        );

        transactionRepository.save(transaction);
    }
}
