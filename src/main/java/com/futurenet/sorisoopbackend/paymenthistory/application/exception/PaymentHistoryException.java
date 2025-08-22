package com.futurenet.sorisoopbackend.paymenthistory.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class PaymentHistoryException extends RestApiException {
    public PaymentHistoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}


