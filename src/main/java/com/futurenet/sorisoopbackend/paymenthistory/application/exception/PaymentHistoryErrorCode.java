package com.futurenet.sorisoopbackend.paymenthistory.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentHistoryErrorCode implements ErrorCode{
    PAYMENT_HISTORY_CREATE_FAIL("PA001","결제 이력 저장에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

