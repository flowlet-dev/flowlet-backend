package com.example.flowlet.presentation.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * APIエラーレスポンスDTO。
 */
@Getter
public class ApiErrorResponse {

    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
