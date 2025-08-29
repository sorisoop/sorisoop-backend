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
    GEMINI_IMAGE_GENERATE_FAIL("CF003", "동화 이미지 생성을 실패했습니다.", HttpStatus.BAD_REQUEST),
    SYNOPSIS_GENERATE_FAIL("CF004", "동화 시놉시스 생성을 실패했습니다.", HttpStatus.BAD_REQUEST),
    SAVE_CUSTOM_FAIRY_TALE_FAIL("CF005", "생성 동화 저장을 실패했습니다.", HttpStatus.BAD_REQUEST),
    SAVE_CUSTOM_FAIRY_TALE_CONTENT_FAIL("CF006", "생성 동화 내용 저장을 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_CUSTOM_FAIRY_TALE_CONTENT_FAIL("CF007", "생성 동화 내용 조회를 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_CUSTOM_FAIRY_TALE_DETAIL_FAIL("CF008", "생성 동화 상세 조회를 실패했습니다.", HttpStatus.BAD_REQUEST),
    DELETE_CUSTOM_FAIRY_TALE_FAIL("CF009", "생성 동화 삭제를 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
