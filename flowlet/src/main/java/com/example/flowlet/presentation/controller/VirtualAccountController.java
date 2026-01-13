package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.VirtualAccountRequest;
import com.example.flowlet.application.dto.VirtualAccountResponse;
import com.example.flowlet.application.service.VirtualAccountApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(path = "/{virtualAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "仮想口座登録", description = "新しい仮想口座を登録します。",
            responses = {
                    @ApiResponse(responseCode = "201", description = "登録成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"目的名は必須です\"}")))
            })
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
    @PutMapping(path = "/{virtualAccountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "仮想口座更新", description = "指定されたIDの仮想口座情報を更新します。",
            responses = {
                    @ApiResponse(responseCode = "200", description = "更新成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"仮想口座が見つかりませんでした。 (ID: 1)\"}")))
            })
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
    @DeleteMapping(path = "/{virtualAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "仮想口座削除", description = "指定されたIDの仮想口座を削除します。")
    public void delete(@PathVariable Long virtualAccountId) {
        virtualAccountApplicationService.delete(virtualAccountId);
    }
}
