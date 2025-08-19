package com.futurenet.sorisoopbackend.member.dto.request;

import com.futurenet.sorisoopbackend.auth.oauth2.dto.response.OAuth2Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OAuthSignupRequest {
    private Long id;
    private String email;
    private String provider;

    public static OAuthSignupRequest from(OAuth2Response oAuth2Response) {
        return OAuthSignupRequest.builder()
                .email(oAuth2Response.getEmail())
                .provider(oAuth2Response.getProvider())
                .build();
    }
}
