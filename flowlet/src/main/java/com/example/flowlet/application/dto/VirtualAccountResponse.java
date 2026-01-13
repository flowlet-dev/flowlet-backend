package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.VirtualAccount;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 仮想口座のレスポンス用DTO。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VirtualAccountResponse(
    Long virtualAccountId,
    String accountName,
    Boolean isLiquid
) {
    /**
     * ドメインモデルからDTOに変換します。
     *
     * @param domain 仮想口座ドメインモデル
     * @return 仮想口座レスポンスDTO
     */
    public static VirtualAccountResponse fromDomain(VirtualAccount domain) {
        return new VirtualAccountResponse(
            domain.virtualAccountId(),
            domain.accountName(),
            domain.isLiquid()
        );
    }
}
