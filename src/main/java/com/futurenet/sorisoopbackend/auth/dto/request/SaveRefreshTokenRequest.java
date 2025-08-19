package com.futurenet.sorisoopbackend.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveRefreshTokenRequest {
    private String refreshToken;
    private Long profileId;
    private Long memberId;
    private String deviceId;
}
