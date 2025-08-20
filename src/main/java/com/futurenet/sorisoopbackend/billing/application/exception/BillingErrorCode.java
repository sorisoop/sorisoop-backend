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
    BILLING_UNKNOWN_ERROR("BI003", "알 수 없는 카드 등록 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_CUSTOMER_TOKEN("BI004","브랜드페이 토큰이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_BILLING_INFO("BI005","재구독을 위한 키가 존재하지 않습니다.",HttpStatus.NOT_FOUND),
    PAYMENT_CONFIRM_FAIL("BI006", "결제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BILLING_CARD_CREATE_FAIL("BI007","카드 등록에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
