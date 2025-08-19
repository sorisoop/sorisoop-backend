package com.futurenet.sorisoopbackend.auth.oauth2.service;

import com.futurenet.sorisoopbackend.auth.dto.UserAuthDto;
import com.futurenet.sorisoopbackend.auth.dto.UserPrincipal;
import com.futurenet.sorisoopbackend.auth.oauth2.dto.response.KakaoResponse;
import com.futurenet.sorisoopbackend.auth.oauth2.dto.response.OAuth2Response;
import com.futurenet.sorisoopbackend.member.application.MemberAuthService;
import com.futurenet.sorisoopbackend.member.application.MemberService;
import com.futurenet.sorisoopbackend.member.dto.request.OAuthSignupRequest;
import com.futurenet.sorisoopbackend.member.dto.request.SignupRequest;
import com.futurenet.sorisoopbackend.member.dto.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberAuthService memberAuthService;
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        FindMemberResponse member = memberService.getMemberByEmail(oAuth2Response.getEmail());

        UserAuthDto userAuthDto = null;

        if (member == null) {
            OAuthSignupRequest oAuthSignupRequest = OAuthSignupRequest.from(oAuth2Response);
            Long memberId = memberAuthService.oAuthSignup(oAuthSignupRequest);
            userAuthDto = new UserAuthDto(memberId, "ROLE_USER");
        } else {
            userAuthDto = new UserAuthDto(member.getId(), "ROLE_USER");
        }

        return new UserPrincipal(userAuthDto);
    }
}
