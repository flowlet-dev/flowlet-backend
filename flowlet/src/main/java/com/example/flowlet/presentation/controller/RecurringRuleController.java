package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.RecurringRuleRequest;
import com.example.flowlet.application.dto.RecurringRuleResponse;
import com.example.flowlet.application.service.RecurringRuleApplicationService;
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
 * 定期実行ルールに関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/recurring-rules")
@RequiredArgsConstructor
@Tag(name = "Recurring Rule", description = "定期実行ルール管理API")
public class RecurringRuleController {

    private final RecurringRuleApplicationService recurringRuleApplicationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "定期実行ルール一覧取得")
    public List<RecurringRuleResponse> findAll() {
        return recurringRuleApplicationService.findAll();
    }

    @GetMapping(path = "/{recurringRuleId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "定期実行ルール取得")
    public RecurringRuleResponse findById(@PathVariable Long recurringRuleId) {
        return recurringRuleApplicationService.findById(recurringRuleId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "定期実行ルール登録",
            responses = {
                    @ApiResponse(responseCode = "201", description = "登録成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"ルール名は必須です\"}")))
            })
    public RecurringRuleResponse create(@RequestBody @Validated RecurringRuleRequest request) {
        return recurringRuleApplicationService.create(request);
    }

    @PutMapping(path = "/{recurringRuleId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "定期実行ルール更新",
            responses = {
                    @ApiResponse(responseCode = "200", description = "更新成功"),
                    @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは業務エラー",
                            content = @Content(schema = @Schema(example = "{\"message\": \"定期実行ルールが見つかりませんでした。 (ID: 1)\"}")))
            })
    public RecurringRuleResponse update(
            @PathVariable Long recurringRuleId,
            @RequestBody @Validated RecurringRuleRequest request) {
        return recurringRuleApplicationService.update(recurringRuleId, request);
    }

    @DeleteMapping(path = "/{recurringRuleId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "定期実行ルール削除")
    public void delete(@PathVariable Long recurringRuleId) {
        recurringRuleApplicationService.delete(recurringRuleId);
    }
}
