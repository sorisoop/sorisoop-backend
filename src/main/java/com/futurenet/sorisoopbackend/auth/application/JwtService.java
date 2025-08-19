package com.futurenet.sorisoopbackend.auth.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtService {
    void reissueAccessToken(HttpServletRequest request, HttpServletResponse response);
}
