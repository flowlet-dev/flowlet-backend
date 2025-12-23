package com.example.flowlet.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    /**
     * 永続化前の処理。
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createAt = now;
        this.updateAt = now;
        this.createBy = "system";
        this.updateBy = "system";
    }

    /**
     * 更新前の処理。
     */
    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
        this.updateBy = "system";
    }

}
