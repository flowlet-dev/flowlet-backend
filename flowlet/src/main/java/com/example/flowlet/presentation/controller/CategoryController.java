package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.CategoryRequest;
import com.example.flowlet.application.dto.CategoryResponse;
import com.example.flowlet.application.service.CategoryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * カテゴリー管理に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "カテゴリー管理API")
public class CategoryController {

    private final CategoryApplicationService categoryApplicationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "カテゴリー一覧取得", description = "親カテゴリーに子カテゴリーがネストされたツリー構造で取得します。")
    public List<CategoryResponse> findAll() {
        return categoryApplicationService.findAllAsTree();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "カテゴリー登録", description = "新しいカテゴリーを登録します。parentCategoryIdを指定することで子カテゴリーとして登録可能です。")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Validated CategoryRequest request) {
        return ResponseEntity.ok(categoryApplicationService.create(request));
    }

    @Operation(summary = "カテゴリー更新", description = "指定されたIDのカテゴリー情報を更新します。名称や親カテゴリーの変更が可能です。")
    @PutMapping("/{categoryCd}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable String categoryCd,
            @RequestBody @Validated CategoryRequest request) {
        return ResponseEntity.ok(categoryApplicationService.update(categoryCd, request));
    }
}
