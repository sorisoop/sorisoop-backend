package com.futurenet.sorisoopbackend.subscription.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class SubscriptionException extends RestApiException {

    public SubscriptionException(ErrorCode errorCode) {
        super(errorCode);
    }
}

