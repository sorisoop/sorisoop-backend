package com.futurenet.sorisoopbackend.mission.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements ErrorCode {

    NOT_PARENT_PROFILE("MI001", "부모 프로필이 아닙니다.", HttpStatus.BAD_REQUEST),
    INVALID_START_DATE("MI002", "시작일은 오늘 날짜보다 전일 수 없습니다.", HttpStatus.BAD_REQUEST),
    SAVE_MISSION_FAIL("MI103", "미션 등록을 실패했습니다", HttpStatus.BAD_REQUEST),
    FIND_MISSION_FAIL("MI104", "미션 조회를 실패했습니다.", HttpStatus.BAD_REQUEST),
    DELETE_MISSION_FAIL("MI105", "미션 삭제를 실패했습니다", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
