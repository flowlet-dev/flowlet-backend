package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.TransactionRequest;
import com.example.flowlet.application.service.TransactionApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 取引に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionApplicationService transactionApplicationService;

    public TransactionController(
            TransactionApplicationService transactionApplicationService
    ) {
        this.transactionApplicationService = transactionApplicationService;
    }

    /**
     * 取引を登録する。
     *
     * @param request 取引登録リクエスト
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody TransactionRequest request) {
        transactionApplicationService.register(request);
    }
}
