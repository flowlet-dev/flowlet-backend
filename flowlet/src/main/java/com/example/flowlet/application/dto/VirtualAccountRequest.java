package com.example.flowlet.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 仮想口座の登録・更新用リクエストDTO。
 */
public record VirtualAccountRequest(
    @NotBlank
    @Size(max = 50)
    String accountName,

    @NotNull
    Boolean isLiquid
) {}
