package com.futurenet.sorisoopbackend.member.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND("ME000", "회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    SIGNUP_FAIL("ME001", "회원가입을 실패했습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST("ME002", "이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST),
    CUSTOMER_KEY_GENERATION_FAIL("ME003", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    CUSTOMER_KEY_UPDATE_FAIL("ME004", "요청을 처리하는 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
