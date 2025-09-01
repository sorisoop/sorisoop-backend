package com.futurenet.sorisoopbackend.notification.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class NotificationException extends RestApiException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
