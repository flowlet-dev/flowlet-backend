package com.example.flowlet.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 実口座の登録・更新用リクエストDTO。
 */
public record PhysicalAccountRequest(
    @NotBlank
    @Size(max = 50)
    String accountName,

    @NotBlank
    @Size(max = 20)
    String accountType
) {}
