package com.futurenet.sorisoopbackend.tts.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TtsErrorCode implements ErrorCode {

    FAIRY_TALE_NOT_FOUND("TS001", "일반 동화를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CUSTOM_FAIRY_TALE_NOT_FOUND("TS002", "사용자 정의 동화를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PAGE_REQUEST("TS003", "잘못된 페이지 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("TS004", "잘못된 요청 데이터입니다.", HttpStatus.BAD_REQUEST),

    PYTHON_SERVER_NO_RESPONSE("TS005", "Python 서버 응답이 없습니다.", HttpStatus.SERVICE_UNAVAILABLE),
    PYTHON_SERVER_ERROR("TS006", "Python 서버 처리 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),

    TTS_GENERATION_FAIL("TS007", "TTS 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    TTS_FETCH_FAIL("TS008", "TTS 오디오를 가져오지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
