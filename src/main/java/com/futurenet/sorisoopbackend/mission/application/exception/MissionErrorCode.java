package com.futurenet.sorisoopbackend.mission.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements ErrorCode {

    INVALID_DATE_RANGE("MI001", "시작일은 마감일보다 이후일 수 없습니다.", HttpStatus.BAD_REQUEST),
    DB_INSERT_FAILED("MI002", "미션 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_MISSION_TYPE("MI003", "알 수 없는 미션 타입입니다.", HttpStatus.BAD_REQUEST),
    PROFILE_NOT_PARENT("MI004", "해당 프로필은 부모가 아닙니다.", HttpStatus.FORBIDDEN),
    PROFILE_NOT_FOUND("MI005", "존재하지 않는 프로필입니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("MI006", "존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND),
    BOOK_COUNT_INVALID("MI007", "올바르지 않은 책 권수입니다.", HttpStatus.BAD_REQUEST),
    FAIRY_TALE_NOT_FOUND("MI008", "존재하지 않는 동화책입니다.", HttpStatus.NOT_FOUND);
    
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
