package com.futurenet.sorisoopbackend.fairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {
    INVALID_PAGE_REQUEST("FV001", "잘못된 페이지 요청입니다.", HttpStatus.BAD_REQUEST),
    FAVORITE_NOT_FOUND("FV002", "즐겨찾기에 존재하지 않는 동화입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
