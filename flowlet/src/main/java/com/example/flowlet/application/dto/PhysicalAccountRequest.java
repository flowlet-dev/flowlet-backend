package com.example.flowlet.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 実口座の登録・更新用リクエストDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhysicalAccountRequest(
    @NotBlank
    @Size(max = 50)
    String accountName,

    @NotBlank
    @Size(max = 20)
    String accountType
) {}
