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
    RESTART_REQUIRES_PAYMENT("SU005","구독을 재시작하려면 새 결제가 필요합니다.", HttpStatus.PAYMENT_REQUIRED),
    CUSTOMER_TOKEN_ISSUE_FAIL("SU006", "결제 수단 등록 중 문제가 발생했습니다.", HttpStatus.BAD_REQUEST),
    CUSTOMER_TOKEN_SAVE_FAIL("SU007", "결제 수단 정보를 저장하지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_CONFIRM_FAIL("SU007", "결제 승인에 실패했습니다.", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR("SU009", "브랜드페이 처리 중 알 수 없는 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_ACTIVE("SU010", "이미 활성화된 구독이 존재합니다.", HttpStatus.CONFLICT),
    CANCEL_FAIL("SU011", "구독 해지 처리에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_STATUS_FAIL("SU012", "구독 상태 변경에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
