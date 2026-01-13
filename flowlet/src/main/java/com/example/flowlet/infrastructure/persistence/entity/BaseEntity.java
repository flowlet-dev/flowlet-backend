package com.example.flowlet.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Generated(event = EventType.INSERT)
    @Column(name = "create_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Tokyo')")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    @Generated(event = {EventType.INSERT, EventType.UPDATE})
    @Column(name = "update_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Tokyo')")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private String updateBy;
}
