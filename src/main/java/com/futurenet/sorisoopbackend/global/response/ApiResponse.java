package com.futurenet.sorisoopbackend.global.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse<T> {
    String code;
    String message;
    T data;
}
