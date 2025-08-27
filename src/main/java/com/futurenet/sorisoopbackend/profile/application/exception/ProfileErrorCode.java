package com.futurenet.sorisoopbackend.profile.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

    SAVE_PROFILE_FAIL("PR000", "프로필 등록을 실패했습니다.", HttpStatus.BAD_REQUEST),
    MISMATCH_PROFILE_ID_AND_MEMBER_ID("PR001", "본인 소유의 프로필이 아닙니다.", HttpStatus.BAD_REQUEST),
    DELETE_PROFILE_FAIL("PR002", "프로필 삭제를 실패했습니다.", HttpStatus.BAD_REQUEST),
    UPDATE_PROFILE_FAIL("PR003", "프로필 수정을 실패했습니다.", HttpStatus.BAD_REQUEST),
    FIND_PROFILE_FAIL("PR004", "프로필 상세 조회를 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
