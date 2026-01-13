package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.PhysicalAccountRequest;
import com.example.flowlet.application.dto.PhysicalAccountResponse;
import com.example.flowlet.application.service.PhysicalAccountApplicationService;
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
 * 実口座に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/physical-accounts")
@RequiredArgsConstructor
@Tag(name = "Physical Account", description = "実口座管理API")
public class PhysicalAccountController {

    private final PhysicalAccountApplicationService physicalAccountApplicationService;

    /**
     * すべての実口座を取得します。
     *
     * @return 実口座レスポンスDTOのリスト
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "実口座一覧取得", description = "登録されているすべての実口座を取得します。")
    public List<PhysicalAccountResponse> findAll() {
        return physicalAccountApplicationService.findAll();
    }

    /**
     * 指定されたIDの実口座を取得します。
     *
     * @param physicalAccountId 実口座ID
     * @return 実口座レスポンスDTO
     */
    @GetMapping(path = "/{physicalAccountId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "実口座取得", description = "指定されたIDの実口座詳細を取得します。")
    public PhysicalAccountResponse findById(@PathVariable Long physicalAccountId) {
        return physicalAccountApplicationService.findById(physicalAccountId);
    }

    /**
     * 実口座を新規登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された実口座レスポンスDTO
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "実口座登録", description = "新しい実口座を登録します。",
            responses = {
                    @ApiResponse(responseCode = "201", description = "登録成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"口座名は必須です\"}")))
            })
    public PhysicalAccountResponse create(@RequestBody @Validated PhysicalAccountRequest request) {
        return physicalAccountApplicationService.create(request);
    }

    /**
     * 実口座を更新します。
     *
     * @param physicalAccountId 実口座ID
     * @param request 更新リクエストDTO
     * @return 更新された実口座レスポンスDTO
     */
    @PutMapping(path = "/{physicalAccountId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "実口座更新", description = "指定されたIDの実口座情報を更新します。",
            responses = {
                    @ApiResponse(responseCode = "200", description = "更新成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"実口座が見つかりませんでした。 (ID: 1)\"}")))
            })
    public PhysicalAccountResponse update(
            @PathVariable Long physicalAccountId,
            @RequestBody @Validated PhysicalAccountRequest request) {
        return physicalAccountApplicationService.update(physicalAccountId, request);
    }

    /**
     * 実口座を削除します。
     *
     * @param physicalAccountId 実口座ID
     */
    @DeleteMapping(path = "/{physicalAccountId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "実口座削除", description = "指定されたIDの実口座を削除します。")
    public void delete(@PathVariable Long physicalAccountId) {
        physicalAccountApplicationService.delete(physicalAccountId);
    }
}
