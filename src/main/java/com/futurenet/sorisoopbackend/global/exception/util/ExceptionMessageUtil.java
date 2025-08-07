package com.futurenet.sorisoopbackend.global.exception.util;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;

public class ExceptionMessageUtil {

    public static String makeValidationExceptionMessage(Exception e) {
        String message = "유효성 검증에 실패했습니다.";

        if (e instanceof MethodArgumentNotValidException manve) {
            message = manve.getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else if (e instanceof HandlerMethodValidationException hmve) {
            message = hmve.getAllErrors().stream()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }

        return message;
    }
}
