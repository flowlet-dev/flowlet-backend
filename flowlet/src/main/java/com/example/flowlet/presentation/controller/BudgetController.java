package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.BudgetRequest;
import com.example.flowlet.application.dto.BudgetResponse;
import com.example.flowlet.application.service.BudgetApplicationService;
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
 * 予算管理に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "予算管理API")
public class BudgetController {

    private final BudgetApplicationService budgetApplicationService;

    /**
     * すべての予算を取得します。
     *
     * @return 予算レスポンスDTOのリスト
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "予算一覧取得", description = "登録されているすべての予算を取得します。")
    public List<BudgetResponse> findAll() {
        return budgetApplicationService.findAll();
    }

    /**
     * 指定されたIDの予算を取得します。
     *
     * @param budgetId 予算ID
     * @return 予算レスポンスDTO
     */
    @GetMapping(path = "/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "予算取得", description = "指定されたIDの予算詳細を取得します。")
    public BudgetResponse findById(@PathVariable Long budgetId) {
        return budgetApplicationService.findById(budgetId);
    }

    /**
     * 予算を登録します。
     *
     * @param request 登録リクエストDTO
     * @return 登録された予算レスポンスDTO
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "予算登録", description = "新しい予算を登録します。同一カテゴリーで期間が重複する予算は登録できません。",
            responses = {
                    @ApiResponse(responseCode = "201", description = "登録成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"カテゴリー「食費」の予算期間が重複しています。\"}")))
            })
    public BudgetResponse create(@RequestBody @Validated BudgetRequest request) {
        return budgetApplicationService.create(request);
    }

    /**
     * 予算を更新します。
     *
     * @param budgetId 予算ID
     * @param request 更新リクエストDTO
     * @return 更新された予算レスポンスDTO
     */
    @PutMapping(path = "/{budgetId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "予算更新", description = "指定されたIDの予算情報を更新します。",
            responses = {
                    @ApiResponse(responseCode = "200", description = "更新成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"予算が見つかりませんでした。 (ID: 1)\"}")))
            })
    public BudgetResponse update(
            @PathVariable Long budgetId,
            @RequestBody @Validated BudgetRequest request) {
        return budgetApplicationService.update(budgetId, request);
    }

    /**
     * 予算を削除します。
     *
     * @param budgetId 予算ID
     */
    @DeleteMapping(path = "/{budgetId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "予算削除", description = "指定されたIDの予算を削除します。")
    public void delete(@PathVariable Long budgetId) {
        budgetApplicationService.delete(budgetId);
    }
}
