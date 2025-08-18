package com.futurenet.sorisoopbackend.billing.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class BillingException extends RestApiException {

    public BillingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
