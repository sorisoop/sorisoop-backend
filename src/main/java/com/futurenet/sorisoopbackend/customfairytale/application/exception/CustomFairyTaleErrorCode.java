package com.futurenet.sorisoopbackend.customfairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomFairyTaleErrorCode implements ErrorCode {

    IMAGE_FILE_NULL("CF000", "이미지가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
