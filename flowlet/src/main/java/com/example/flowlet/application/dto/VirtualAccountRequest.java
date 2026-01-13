package com.example.flowlet.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 仮想口座の登録・更新用リクエストDTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VirtualAccountRequest(
    @NotBlank
    @Size(max = 50)
    String accountName,

    @NotNull
    Boolean isLiquid
) {}
