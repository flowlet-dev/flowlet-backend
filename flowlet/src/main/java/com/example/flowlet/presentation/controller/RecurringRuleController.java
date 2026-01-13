package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.RecurringRuleRequest;
import com.example.flowlet.application.dto.RecurringRuleResponse;
import com.example.flowlet.application.service.RecurringRuleApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @Operation(summary = "定期実行ルール一覧取得")
    public List<RecurringRuleResponse> findAll() {
        return recurringRuleApplicationService.findAll();
    }

    @GetMapping("/{recurringRuleId}")
    @Operation(summary = "定期実行ルール取得")
    public RecurringRuleResponse findById(@PathVariable Long recurringRuleId) {
        return recurringRuleApplicationService.findById(recurringRuleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "定期実行ルール登録")
    public RecurringRuleResponse create(@RequestBody @Validated RecurringRuleRequest request) {
        return recurringRuleApplicationService.create(request);
    }

    @PutMapping("/{recurringRuleId}")
    @Operation(summary = "定期実行ルール更新")
    public RecurringRuleResponse update(
            @PathVariable Long recurringRuleId,
            @RequestBody @Validated RecurringRuleRequest request) {
        return recurringRuleApplicationService.update(recurringRuleId, request);
    }

    @DeleteMapping("/{recurringRuleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "定期実行ルール削除")
    public void delete(@PathVariable Long recurringRuleId) {
        recurringRuleApplicationService.delete(recurringRuleId);
    }
}
