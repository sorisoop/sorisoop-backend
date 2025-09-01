package com.futurenet.sorisoopbackend.notification.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

    UPDATE_NOTIFICATION_STATUS_FAIL("NF000", "알림 허용 여부 변경을 실패했습니다.", HttpStatus.BAD_REQUEST),
    READ_NOTIFICATION_FAIL("NF001", "알림 읽기를 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
