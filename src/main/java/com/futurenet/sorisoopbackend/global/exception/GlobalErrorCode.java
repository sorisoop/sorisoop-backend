package com.futurenet.sorisoopbackend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    INVALID_URL("GL000", "유효하지 않은 URL", HttpStatus.BAD_REQUEST),
    JSON_PROCESSING_FAIL("GL001", "json 파싱 실패", HttpStatus.BAD_REQUEST),
    INVALID_CONTENT_TYPE("GL002", "파일 Content-Type이 잘못되었습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
