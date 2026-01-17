package com.example.flowlet.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * カテゴリー登録・更新リクエストDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryRequest(
    @NotBlank
    @Size(max = 50)
    String categoryName,

    String parentCategoryCd,

    @NotBlank
    String flowType
) {}