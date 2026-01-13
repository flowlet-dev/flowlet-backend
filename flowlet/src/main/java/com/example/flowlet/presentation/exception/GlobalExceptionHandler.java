package com.example.flowlet.presentation.exception;

import com.example.flowlet.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;

/**
 * アプリケーション全体の例外をハンドリングするクラス。
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * ビジネス例外をハンドリングします。
     *
     * @param e      ビジネス例外
     * @param locale ロケール
     * @return エラーレスポンス
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException e, Locale locale) {
        String message = messageSource.getMessage(e.getMessageCode(), e.getArgs(), locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", message));
    }

    /**
     * その他の予期しない例外をハンドリングします。
     *
     * @param e      例外
     * @param locale ロケール
     * @return エラーレスポンス
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e, Locale locale) {
        String message = messageSource.getMessage("error.unexpected", null, locale);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", message));
    }
}
