package com.futurenet.sorisoopbackend.billing.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BillingErrorCode implements ErrorCode {
    CUSTOMER_KEY_GENERATION_FAIL("BI001", "키 생성에 실패했습니다.",HttpStatus.INTERNAL_SERVER_ERROR),
    BILLING_KEY_ISSUE_FAIL("BI002", "카드 등록(빌링키 발급)에 실패했습니다.", HttpStatus.BAD_REQUEST),
    CARD_SAVE_FAIL("BI003", "카드 정보를 저장하지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BILLING_UNKNOWN_ERROR("BI004", "알 수 없는 카드 등록 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_METHOD_FETCH_FAIL("BI005", "카드 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_CARD("BI006","이미 등록된 카드입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
