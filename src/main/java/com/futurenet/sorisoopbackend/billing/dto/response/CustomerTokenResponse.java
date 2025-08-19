package com.futurenet.sorisoopbackend.billing.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerTokenResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
    private LocalDateTime expiresAt;
}