package com.futurenet.sorisoopbackend.billing.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BillingErrorCode implements ErrorCode {
    CUSTOMER_KEY_GENERATION_FAIL("BI001", "키 생성에 실패했습니다.",HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
