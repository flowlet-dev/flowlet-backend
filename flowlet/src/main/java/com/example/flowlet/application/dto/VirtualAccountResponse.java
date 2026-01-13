package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.VirtualAccount;

/**
 * 仮想口座のレスポンス用DTO。
 */
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
