package com.futurenet.sorisoopbackend.brandpaytoken.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerTokenResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
    private LocalDateTime expiresAt;
}