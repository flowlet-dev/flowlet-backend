package com.example.flowlet.domain.model.user;

import java.util.Objects;

/**
 * ユーザー名を表す値オブジェクト。
 *
 * <p>
 * ユーザー名は「姓」と「名」から構成され、
 * 値としての同一性を持つ。
 * </p>
 *
 * <p>
 * 本クラスは不変であり、状態を変更することはできない。
 * </p>
 *
 * @param lastName  姓
 * @param firstName 名
 */
public record UserName(String lastName, String firstName) {

    /**
     * コンストラクタ。
     *
     * <p>
     * 不正な値（null / 空文字）を防ぐため、
     * コンストラクタ内でバリデーションを行う。
     * </p>
     *
     * @throws IllegalArgumentException 不正なユーザー名の場合
     */
    public UserName {
        if (isBlank(lastName)) {
            throw new IllegalArgumentException("Last name must not be blank.");
        }
        if (isBlank(firstName)) {
            throw new IllegalArgumentException("First name must not be blank.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
