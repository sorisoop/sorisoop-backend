package com.futurenet.sorisoopbackend.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();
    String getCode();
    HttpStatus getHttpStatus();
}
