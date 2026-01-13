package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.VirtualAccountRequest;
import com.example.flowlet.application.dto.VirtualAccountResponse;
import com.example.flowlet.application.service.VirtualAccountApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仮想口座に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/virtual-accounts")
@RequiredArgsConstructor
@Tag(name = "Virtual Account", description = "仮想口座（目的別管理）API")
public class VirtualAccountController {

    private final VirtualAccountApplicationService virtualAccountApplicationService;

    /**
     * すべての仮想口座を取得します。
     *
     * @return 仮想口座レスポンスDTOのリスト
     */
    @GetMapping
    @Operation(summary = "仮想口座一覧取得", description = "登録されているすべての仮想口座を取得します。")
    public List<VirtualAccountResponse> findAll() {
        return virtualAccountApplicationService.findAll();
    }

    /**
     * 指定されたIDの仮想口座を取得します。
     *
     * @param virtualAccountId 仮想口座ID
     * @return 仮想口座レスポンスDTO
     */
    @GetMapping("/{virtualAccountId}")
    @Operation(summary = "仮想口座取得", description = "指定されたIDの仮想口座詳細を取得します。")
    public VirtualAccountResponse findById(@PathVariable Long virtualAccountId) {
        return virtualAccountApplicationService.findById(virtualAccountId);
    }

    /**
     * 仮想口座を新規登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された仮想口座レスポンスDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "仮想口座登録", description = "新しい仮想口座を登録します。")
    public VirtualAccountResponse create(@RequestBody @Validated VirtualAccountRequest request) {
        return virtualAccountApplicationService.create(request);
    }

    /**
     * 仮想口座を更新します。
     *
     * @param virtualAccountId 仮想口座ID
     * @param request 更新リクエストDTO
     * @return 更新された仮想口座レスポンスDTO
     */
    @PutMapping("/{virtualAccountId}")
    @Operation(summary = "仮想口座更新", description = "指定されたIDの仮想口座情報を更新します。")
    public VirtualAccountResponse update(
            @PathVariable Long virtualAccountId,
            @RequestBody @Validated VirtualAccountRequest request) {
        return virtualAccountApplicationService.update(virtualAccountId, request);
    }

    /**
     * 仮想口座を削除します。
     *
     * @param virtualAccountId 仮想口座ID
     */
    @DeleteMapping("/{virtualAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "仮想口座削除", description = "指定されたIDの仮想口座を削除します。")
    public void delete(@PathVariable Long virtualAccountId) {
        virtualAccountApplicationService.delete(virtualAccountId);
    }
}
