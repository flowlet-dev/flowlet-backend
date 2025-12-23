package com.example.flowlet.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractAuditableEntity {
    @Size(max = 10)
    @NotNull
    @Column(name = "create_by", nullable = false, length = 10)
    private String createBy;

    @NotNull
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Size(max = 10)
    @NotNull
    @Column(name = "update_by", nullable = false, length = 10)
    private String updateBy;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    protected AbstractAuditableEntity() {
    }

}
