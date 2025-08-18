package com.futurenet.sorisoopbackend.voice.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VoiceErrorCode implements ErrorCode {

    S3_FILE_UPLOAD_FAIL("VO000", "S3 파일 업로드 실패", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED("VO001", "업로드 가능한 파일 크기를 초과했습니다.", HttpStatus.PAYLOAD_TOO_LARGE);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
