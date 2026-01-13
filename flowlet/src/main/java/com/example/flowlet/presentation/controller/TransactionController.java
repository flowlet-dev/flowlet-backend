package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.TransactionRequest;
import com.example.flowlet.application.dto.TransactionResponse;
import com.example.flowlet.application.service.TransactionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 取引に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction", description = "取引（収支・振替）管理API")
public class TransactionController {

    private final TransactionApplicationService transactionApplicationService;

    /**
     * すべての取引を取得します。
     *
     * @return 取引レスポンスDTOのリスト
     */
    @GetMapping
    @Operation(summary = "取引一覧取得", description = "登録されているすべての取引を取得します。")
    public List<TransactionResponse> findAll() {
        return transactionApplicationService.findAll();
    }

    /**
     * 指定されたIDの取引を取得します。
     *
     * @param transactionId 取引ID
     * @return 取引レスポンスDTO
     */
    @GetMapping("/{transactionId}")
    @Operation(summary = "取引取得", description = "指定されたIDの取引詳細を取得します。")
    public TransactionResponse findById(@PathVariable Long transactionId) {
        return transactionApplicationService.findById(transactionId);
    }

    /**
     * 取引を登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された取引レスポンスDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "取引登録", description = "新しい取引（およびその明細）を登録します。")
    public TransactionResponse create(@RequestBody @Validated TransactionRequest request) {
        return transactionApplicationService.create(request);
    }

    /**
     * 取引を削除します。
     *
     * @param transactionId 取引ID
     */
    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "取引削除", description = "指定されたIDの取引を削除します。")
    public void delete(@PathVariable Long transactionId) {
        transactionApplicationService.delete(transactionId);
    }
}
