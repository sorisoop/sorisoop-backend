package com.futurenet.sorisoopbackend.brandpaytoken.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BrandPayTokenErrorCode implements ErrorCode {
    BRANDPAY_TOKEN_ISSUE_FAIL("BP001", "토큰 발급에 실패했습니다.", HttpStatus.BAD_GATEWAY);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
