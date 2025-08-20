package com.futurenet.sorisoopbackend.subscription.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SubscriptionErrorCode implements ErrorCode {
    NOT_FOUND_PLAN("SU001", "해당하는 구독 플랜이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    SUBSCRIPTION_CREATE_FAIL("SU002", "구독 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_SUBSCRIPTION("SU003", "기존 구독 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_STATUS("SU004", "올바르지 않은 상태입니다.",HttpStatus.BAD_REQUEST),
    RESTART_REQUIRES_PAYMENT("SU005","구독을 재시작하려면 새 결제가 필요합니다.", HttpStatus.PAYMENT_REQUIRED);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
