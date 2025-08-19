package com.futurenet.sorisoopbackend.member.application;

import com.futurenet.sorisoopbackend.member.dto.request.OAuthSignupRequest;
import com.futurenet.sorisoopbackend.member.dto.request.SignupRequest;

public interface MemberAuthService {
    Long oAuthSignup(OAuthSignupRequest request);
    void signup(SignupRequest request);
}
