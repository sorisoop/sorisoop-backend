package com.futurenet.sorisoopbackend.auth.presentation;

import com.futurenet.sorisoopbackend.auth.application.JwtService;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        jwtService.reissueAccessToken(request, response);
        return ResponseEntity.ok(new ApiResponse<>("AU100", "엑세스토큰 재발급 성공", null));
    }
}
