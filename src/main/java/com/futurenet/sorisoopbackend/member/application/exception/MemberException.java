package com.futurenet.sorisoopbackend.member.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class MemberException extends RestApiException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
