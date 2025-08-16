package com.futurenet.sorisoopbackend.customfairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomFairyTaleErrorCode implements ErrorCode {

    IMAGE_FILE_NULL("CF000", "이미지가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    OPENAI_SCRIPT_RESPONSE_NULL("CF001", "스크립트 생성 결과가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    OPENAI_CHARACTER_EXTRACT_RESPONSE_NULL("CF002", "캐릭터 특징 추출 결과가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    OPENAI_IMAGE_GENERATE_FAIL("CF003", "동화 이미지 생성을 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
