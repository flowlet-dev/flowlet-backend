package com.example.flowlet.presentation.controller;

import com.example.flowlet.application.dto.UserSettingRequest;
import com.example.flowlet.application.dto.UserSettingResponse;
import com.example.flowlet.application.service.UserSettingApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザー設定に関するAPIを提供するコントローラー。
 */
@RestController
@RequestMapping("/api/user-settings")
@RequiredArgsConstructor
@Tag(name = "User Setting", description = "ユーザー設定管理API")
public class UserSettingController {

    private final UserSettingApplicationService userSettingApplicationService;

    @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "設定取得", description = "指定されたキーの設定値を取得します。")
    public UserSettingResponse findByKey(@PathVariable String key) {
        return userSettingApplicationService.findByKey(key);
    }

    @PutMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "設定更新", description = "締め日(closing_day)などの設定を更新します。")
    public UserSettingResponse update(@PathVariable String key, @RequestBody UserSettingRequest request) {
        return userSettingApplicationService.update(key, request);
    }
}
