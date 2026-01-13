package com.example.flowlet.application.transaction;

import com.example.flowlet.application.transaction.dto.TransactionRequest;
import com.example.flowlet.domain.category.CategoryRepository;
import com.example.flowlet.domain.transaction.Transaction;
import com.example.flowlet.domain.transaction.TransactionRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 取引に関するユースケースを提供するアプリケーションサービス。
 */
@Service
public class TransactionApplicationService {

    private final TransactionRepository transactionRepository;
    @Getter
    private final CategoryRepository categoryRepository;

    public TransactionApplicationService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 取引を登録する
     *
     * @param request 取引登録リクエスト
     */
    @Transactional
    public void register(TransactionRequest request) {

        // Note: In Pattern 2, we create the Domain Model.
        // For simplicity in this step, we just create a Transaction with no details for now.
        // In a real scenario, we would map the request to a full Transaction object.
        Transaction transaction = new Transaction(
                null,
                request.getTransactionDate(),
                request.getDescription(),
                new ArrayList<>()
        );

        transactionRepository.save(transaction);
    }

}
