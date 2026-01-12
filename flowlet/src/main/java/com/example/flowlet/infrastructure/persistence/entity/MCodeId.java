package com.example.flowlet.infrastructure.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * MCode の褁E主キーE
 */
public class MCodeId implements Serializable {
    private String codeGroup;
    private String codeValue;

    public MCodeId() {
    }

    public MCodeId(String codeGroup, String codeValue) {
        this.codeGroup = codeGroup;
        this.codeValue = codeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCodeId mCodeId = (MCodeId) o;
        return Objects.equals(codeGroup, mCodeId.codeGroup) && Objects.equals(codeValue, mCodeId.codeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeGroup, codeValue);
    }
}
