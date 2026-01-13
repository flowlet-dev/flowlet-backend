package com.example.flowlet.domain.exception;

import lombok.Getter;

/**
 * ビジネスロジックにおける例外を表す基本クラス。
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final String messageCode;
    private final Object[] args;

    /**
     * メッセージコードを指定して例外を生成します。
     *
     * @param messageCode メッセージコード
     */
    public BusinessException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = null;
    }

    /**
     * メッセージコードと引数を指定して例外を生成します。
     *
     * @param messageCode メッセージコード
     * @param args メッセージ引数
     */
    public BusinessException(String messageCode, Object... args) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = args;
    }
}
