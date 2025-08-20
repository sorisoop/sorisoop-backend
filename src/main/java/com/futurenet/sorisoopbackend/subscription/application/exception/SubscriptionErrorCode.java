package com.futurenet.sorisoopbackend.subscription.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SubscriptionErrorCode implements ErrorCode {
    NOT_FOUND_PLAN("SU001", "해당하는 구독 플랜이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    SUBSCRIPTION_CREATE_FAIL("SU002", "구독 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
