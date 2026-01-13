package com.example.flowlet.application.dto;

import com.example.flowlet.domain.model.PhysicalAccount;

/**
 * 実口座のレスポンス用DTO。
 */
public record PhysicalAccountResponse(
    Long physicalAccountId,
    String accountName,
    String accountType
) {
    /**
     * ドメインモデルからDTOに変換します。
     *
     * @param domain 実口座ドメインモデル
     * @return 実口座レスポンスDTO
     */
    public static PhysicalAccountResponse fromDomain(PhysicalAccount domain) {
        return new PhysicalAccountResponse(
            domain.physicalAccountId(),
            domain.accountName(),
            domain.accountType()
        );
    }
}
