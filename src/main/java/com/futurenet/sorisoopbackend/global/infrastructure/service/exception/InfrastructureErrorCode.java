package com.futurenet.sorisoopbackend.global.infrastructure.service.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InfrastructureErrorCode implements ErrorCode {

    S3_FILE_UPLOAD_FAIL("IF000", "S3 파일 업로드 실패", HttpStatus.BAD_REQUEST),
    S3_FILE_DELECT_FAIL("IF101", "S3 파일 삭제 실패", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
