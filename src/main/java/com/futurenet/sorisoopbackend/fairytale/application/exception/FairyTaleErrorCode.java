package com.futurenet.sorisoopbackend.fairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FairyTaleErrorCode implements ErrorCode {
    SAVE_FAIRY_TALE_CONTENT_FAIL("FT001", "일반 동화 내용 저장을 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_FAIRY_TALE_CONTENT_FAIL("FT002", "일반 동화 내용 조회를 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_FAIRY_TALE_DETAIL_FAIL("FT003", "일반 동화 상세 조회를 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_FAIRY_TALE_CATEGORY_FAIL("FT004", "동화 카테고리 조회를 실패했습니다.", HttpStatus.NOT_FOUND),
    FIND_FAIRY_TALE_LIST_FAIL("FT005", "동화 목록 조회를 실패했습니다.", HttpStatus.NOT_FOUND),
    INVALID_PAGE_REQUEST("FT006", "잘못된 페이지 요청입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
