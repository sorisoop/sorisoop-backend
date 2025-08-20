package com.futurenet.sorisoopbackend.auth.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    REFRESH_ERROR("AU003", "리프레시 토큰 에러입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_ISSUE_FAIL("AU004", "토큰 발급을 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
