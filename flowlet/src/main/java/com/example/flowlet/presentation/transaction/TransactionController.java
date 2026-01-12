package com.example.flowlet.presentation.transaction;

import com.example.flowlet.application.transaction.dto.TransactionRequest;
import com.example.flowlet.application.transaction.TransactionApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 取引に関するAPIを提供するコントローラーE
 */
@CrossOrigin(origins = "http://localhost:5173")
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
     * 取引を登録するE
     *
     * @param request 取引登録リクエスチE
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody TransactionRequest request) {
        transactionApplicationService.register(request);
    }
}
