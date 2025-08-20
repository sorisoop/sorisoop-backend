package com.futurenet.sorisoopbackend.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.io.IOException;

public class ResponseUtil {

    public static void setResponse(HttpServletResponse response, String code, String message, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>(code, message, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }


    public static String createResponseCookie(String key, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(true)
                //.secure(true)
                .path("/")
                .maxAge(maxAge)
                //.sameSite("None")
                //.domain(".n-e.kr")
                .build();

        return cookie.toString();
    }
}
